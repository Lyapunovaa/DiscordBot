package discordBot;

import apiClient.RequestToAPI;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
       /* String token = System.getenv("BOT_TOKEN");
        DiscordClient client = DiscordClient.create(token);
        GatewayDiscordClient gateway = client.login().block();

        assert gateway != null;
        gateway.on(MessageCreateEvent.class).subscribe(event ->
        {
            Message message = event.getMessage();
            if ("!ping".equals(message.getContent())) {
                MessageChannel channel = message.getChannel().block();
                channel.createMessage("pong").block();
            }
        });

        gateway.onDisconnect().block();*/

        RequestToAPI req = new RequestToAPI();
        req.RequestToAPI();


    }
}