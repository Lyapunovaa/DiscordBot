package discordBot.commands;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discordBot.utils.SlashCommand;
import reactor.core.publisher.Mono;

public class PingCommand implements SlashCommand {

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        //We reply to the command with "Pong!" and make sure it is ephemeral (only the command user can see it)
        return event.reply().withEphemeral(true).withContent("Послушай музыку, будь крутым!");
    }
}