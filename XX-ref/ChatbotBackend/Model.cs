using System.Text.Json.Serialization;
using Microsoft.EntityFrameworkCore;

public class Message {
    public int ID { get; set; }
    public string message { get; set; } = "";
    public bool isMine { get; set; }
    public int orderNumber { get; set; }
    public int messageHistoryID {get; set;}
}

public class MessageHistory {
    public int ID { get; set; }
    public List<Message> Messages { get; set; } = new();
    public string HistoryName { get; set; } = "";
}

public class HistoryContext: DbContext {
    public HistoryContext(DbContextOptions<HistoryContext> options) : base(options) {}
    public DbSet<Message> Message => Set<Message>();
    public DbSet<MessageHistory> MessageHistory => Set<MessageHistory>();
}