package discordBot.commands;


import apiClient.api.TokenUpdater;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discordBot.utils.SlashCommand;
import reactor.core.publisher.Mono;

//Команда обновления Iam токена, чтобы можно было вручную обновить токен
public class UpdateTokenCommand implements SlashCommand {
    static TokenUpdater tokenUpdater = new TokenUpdater();

    public Mono<Message> isSuccessUpdate(ChatInputInteractionEvent event) {
        tokenUpdater.updateToken();
        return event.editReply("TokenUpdated");
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        event.deferReply().subscribe();
        return isSuccessUpdate(event).then();
    }
}
