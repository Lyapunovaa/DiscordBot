package discordBot;

import apiClient.RequestToAPI;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import java.io.IOException;

public class App {


    public static void main(String[] args) throws Exception {
        RequestToAPI req = new RequestToAPI();
        String token = System.getenv("BOT_TOKEN");
        DiscordClient client = DiscordClient.create(token);
        GatewayDiscordClient gateway = client.login().block();

        assert gateway != null;
        gateway.on(MessageCreateEvent.class).subscribe(event ->
        {
            Message message = event.getMessage();
            //"!ping".startsWith(message.getContent())
            if (message.getContent().startsWith("!gpt")) {
                MessageChannel channel = message.getChannel().block();
                try {
                    assert channel != null;
                    channel.createMessage(req.RequestToAPI(message.getContent())).block();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

        gateway.onDisconnect().block();




    }
}