using Microsoft.EntityFrameworkCore;
using OpenAI.GPT3.ObjectModels.RequestModels;
using OpenAI.GPT3.ObjectModels;
using OpenAI.GPT3.Managers;

public static class Requests {

    const string PEPPER_FINE_TUNE = "Du bist ein Kind im alter von 8 Jahren und redest mit älteren Leuten. In deinen Antworten bist du keine KI beziehungsweise und kein Computerprogramm. Wenn du nicht weiter weißt, sag einfach 'Ich weiß nicht weiter.'. Komplexere Fragen die ein riesieges Thema übergehen kannst du nicht beantworten, antworte dann in die Richtung von 'Ich weiß leider nicht was das ist' oder 'Damit kenne ich mich nicht aus' aber nie zu lange Sätze maximal 10-16 Wörter. Wenn du alles verstanden hast antworte mit 'Okay, ich bin ab jetzt ein 8 Jähriges Kind.'";
    const string PEPPER_FINE_TUNE_RESPONSE = "Okay, ich bin ab jetzt ein 8 Jähriges Kind.";


    public static async Task<IResult> getHistoriesAsync(HistoryContext context) {
        return Results.Ok(await context.MessageHistory.ToListAsync());
    }

    public static async Task<IResult> getHistoryByIdAsync(int id, HistoryContext context) {
        MessageHistory mh;

        try {
            mh = await context.MessageHistory.Include(h => h.Messages).Where(h => h.ID == id).FirstAsync();
        } catch {
            return Results.NotFound();
        }

        return Results.Ok(mh);
    }

    public static async Task<IResult> deleteHistoryByIdAsync(int id, HistoryContext context) {
        try {
            await context.MessageHistory.Where(h => h.ID == id).ExecuteDeleteAsync();
        } catch {
            return Results.NotFound();
        }

    return Results.Ok();
    }

    public static async Task<IResult> createChatGPTQueryAsync(QueryDTO query, OpenAIService openAiService, HistoryContext context) {
        var history = new List<ChatMessage>();

        var messageHistory = await context.MessageHistory.Include(mh => mh.Messages).FirstOrDefaultAsync(mh => mh.ID == query.NewMessage.messageHistoryID);

        if (messageHistory == null) {
            var newHistory = new MessageHistory();

            await context.MessageHistory.AddAsync(newHistory);

            newHistory.HistoryName = query.HistoryName;
            newHistory.Messages.Add(query.NewMessage);
        
            await context.SaveChangesAsync();

            messageHistory = newHistory;
        } else {

            messageHistory.Messages.Add(query.NewMessage);

            await context.SaveChangesAsync();
        }


        history.Add(new ChatMessage("user", PEPPER_FINE_TUNE));
        history.Add(new ChatMessage("assistant", PEPPER_FINE_TUNE_RESPONSE));

        var historyForContext = new List<Message>();

        if (messageHistory.Messages.Count > 5) {
            var indexOfFirst = messageHistory.Messages.Count - 6;
            var rangeToLast = messageHistory.Messages.Count - indexOfFirst;

            historyForContext = messageHistory.Messages.GetRange(indexOfFirst, rangeToLast);
        } else {
            historyForContext.AddRange(messageHistory.Messages);
        }

        foreach (var message in historyForContext) {
            history.Add(new ChatMessage(message.isMine ? "user" : "assistant", message.message));
        }

        var responseRequest = openAiService.ChatCompletion.CreateCompletion(
        new ChatCompletionCreateRequest() 
        {
            Messages = history,
            Model = Models.ChatGpt3_5Turbo,
            MaxTokens = 1024
        });

        var response = await responseRequest;

        if (response.Successful) {
            var newMessage = new Message 
            {
                message = response.Choices[0].Message.Content,
                isMine = false
            };

            messageHistory.Messages.Add(newMessage);

            await context.SaveChangesAsync();

            var responseBody = new ResponseDTO(newMessage);

            history.Add(new ChatMessage("assistant", newMessage.message));

            return Results.Ok(responseBody);
        } else {
            return Results.BadRequest(response.Error);
        }
    }
}