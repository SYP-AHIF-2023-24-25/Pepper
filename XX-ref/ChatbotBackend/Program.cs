using OpenAI.GPT3;
using OpenAI.GPT3.Managers;
using Microsoft.EntityFrameworkCore;


var allowSpecificOrigins = "_allowSpecificOrigins";

var builder = WebApplication.CreateBuilder(args);

var openAiService = new OpenAIService(new OpenAiOptions()
{
    ApiKey =  Environment.GetEnvironmentVariable("OPENAI_KEY")!
});

builder.Services.AddDbContext<HistoryContext>(options =>
    options.UseSqlServer(Environment.GetEnvironmentVariable("CONNECTION_STRING")!));

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options => {
    options.SwaggerDoc("v1", new() {Title = "Visitor Assistant ChatBot API", Version = "v1"});
});

builder.Services.AddCors(options =>
{
    options.AddPolicy(name: allowSpecificOrigins,
        builder =>
        {
            builder.WithOrigins("*")
                .AllowAnyHeader()
                .AllowAnyMethod();
        });
});

builder.Services.AddControllers();

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();

app.UseCors(allowSpecificOrigins);


app.MapGet("/histories", async (HistoryContext context) => await Requests.getHistoriesAsync(context))
    .Produces<List<MessageHistory>>()
    .WithOpenApi(config => {
        config.Description = "Only the IDs and Names of the saved Histories";

        return config;
    });

app.MapGet("/histories/{id}", async (int id, HistoryContext context) => await Requests.getHistoryByIdAsync(id, context))
    .Produces<List<MessageHistory>>()
    .WithOpenApi(config => {
        config.Description = "History with given ID and all Messages aswell";

        return config;
    });;

app.MapDelete("/histories/{id}", async (int id, HistoryContext context) => await Requests.deleteHistoryByIdAsync(id, context));

app.MapPost("/query", async (QueryDTO query, HistoryContext context) => await Requests.createChatGPTQueryAsync(query, openAiService, context));

app.Run();
