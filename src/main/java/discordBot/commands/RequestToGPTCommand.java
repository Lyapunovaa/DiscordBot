package discordBot.commands;

import apiClient.RequesterToYaGPT;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class RequestToGPTCommand implements SlashCommand {

   static RequesterToYaGPT requester = new RequesterToYaGPT();


    private static Mono<Message> methodThatTakesALongTime(ChatInputInteractionEvent event, String textMessage) {
        String responseMessage = requester.RequestToAPI(textMessage);
        return event.createFollowup(STR."Запрос - \{textMessage} \n\n\{responseMessage}");
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        var message = event.getOption("message")
                .flatMap(it -> it.getValue() )
                .map(it -> it.asString())
                .orElseThrow((() -> new IllegalArgumentException("empty message body")));
        return event.deferReply().withEphemeral(false).then(methodThatTakesALongTime(event,message).then());

    }
}
