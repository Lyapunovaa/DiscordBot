package discordBot.commands;

import apiClient.RequesterToYaGPT;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class RequestToGPTCommand implements SlashCommand {

    RequesterToYaGPT requester;

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        var message = event.getOption("message")
                .flatMap(it -> it.getValue() )
                .map(it -> it.asString())
                .orElseThrow((() -> new IllegalArgumentException("empty message body")));
        System.out.println(message);

        //requester.RequestToAPI(message);

        return event.reply()
                .withEphemeral(false)
                .withContent("new cash");
    }
}
