package discordBot.utils;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import reactor.core.publisher.Mono;

/**
 * A simple interface defining our slash command class contract.
 * handle() method which will house all the logic for processing each command.
 */
public interface SlashCommand {

    Mono<Void> handle(ChatInputInteractionEvent event);
}