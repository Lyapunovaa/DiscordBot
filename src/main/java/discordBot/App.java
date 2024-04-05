package discordBot;

import apiClient.RequesterToYaGPT;
import apiClient.utils.Properties;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discordBot.commands.CommandEnum;

public class App {


    public static void main(String[] args) throws Exception {
        RequesterToYaGPT req = new RequesterToYaGPT();
        String token = Properties.properties.discordToken();
        DiscordClient client = DiscordClient.create(token);
        GatewayDiscordClient gateway = client.login().block();

        //Регистрируем все команды из enum в дискорде
        for (CommandEnum c : CommandEnum.values()){
            final ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder()
                    .name(c.commandName)
                    .description(c.description)
                    .addAllOptions(c.agruments)
                    .build();
            gateway.getRestClient().getApplicationService()
                    .createGlobalApplicationCommand(gateway.getRestClient().getApplicationId().block(), commandRequest)
                    .block();
        }


        //слушаем все сообщения и проверяем которые начинаются на  !gpt
        assert gateway != null;
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();
            MessageChannel channel = message.getChannel().block();
            if (message.getContent().startsWith("!gpt")) {
                try {
                    assert channel != null;
                    channel.createMessage(req.RequestToAPI(message.getContent())).block();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //слушаем все эвенты, если это команда то отвечаем
        gateway.on(ChatInputInteractionEvent.class, event -> {
            return CommandEnum.of(event.getCommandName()).handle(event);
        }).subscribe();
        gateway.onDisconnect().block();
    }
}