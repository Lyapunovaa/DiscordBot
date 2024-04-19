package discordBot;

import apiClient.api.RequesterToYaGPT;
import apiClient.utils.Properties;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import discordBot.commands.CommandEnum;

public class App {


    public static void main(String[] args) throws Exception {
        RequesterToYaGPT req = new RequesterToYaGPT();
        String token = Properties.properties.discordToken();
        DiscordClient client = DiscordClient.create(token);
        GatewayDiscordClient gateway = client.login().block();



        //Регистрируем все команды из enum в дискорде
        for (CommandEnum c : CommandEnum.values()) {
            final ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder().name(c.commandName).description(c.description).addAllOptions(c.agruments).build();
            gateway.getRestClient().getApplicationService().createGlobalApplicationCommand(gateway.getRestClient().getApplicationId().block(), commandRequest).block();
        }



        //TODO удалить после удаления команд
        /*
          gateway.getRestClient().getApplicationService()
                .getGlobalApplicationCommands(gateway.getRestClient().getApplicationId().block())
                .filter(command -> command.name().equals("gpt1"))
                .flatMap(command -> gateway.getRestClient().getApplicationService()
                     .deleteGlobalApplicationCommand(gateway.getRestClient().getApplicationId().block(), command.id().asLong()));
        */

        // TODO слушаем все сообщения и проверяем которые начинаются в startWith. В дальнейшем можно использовать
         /*assert gateway != null;
        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            Message message = event.getMessage();
            MessageChannel channel = message.getChannel().block();
            if (message.getContent().toLowerCase().startsWith("")) {
                try {
                    assert channel != null;
                    channel.createMessage(messageSpec -> {
                        messageSpec.addFile()
                    })
                    channel.createMessage().block();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });*/



        //слушаем все эвенты, если это команда то отвечаем
        gateway.on(ChatInputInteractionEvent.class, event -> {
            return CommandEnum.of(event.getCommandName()).handle(event);
        }).subscribe();
        gateway.onDisconnect().block();
    }
}