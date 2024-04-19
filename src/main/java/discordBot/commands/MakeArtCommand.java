package discordBot.commands;

import apiClient.api.RequesterToYaART;
import apiClient.dto.DTO_ART;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateFields;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.util.List;

public class MakeArtCommand implements SlashCommand {

    static RequesterToYaART requester = new RequesterToYaART();

    private static Mono<Message> methodThatTakesALongTime(ChatInputInteractionEvent event) {
        var message = event.getOption("message")
                .flatMap(it -> it.getValue())
                .map(it -> it.asString())
                .orElseThrow((() -> new IllegalArgumentException("empty message body")));

        byte[] decodedImage = requester.sendRequestToGenerateImage(DTO_ART.RequestArtDto.builder()
                .messages(List.of(
                                DTO_ART.MessagesArtDto.builder().text(message).build()
                        )
                ).generation_options(DTO_ART.GenerationOptionsArtDto.builder().build())
                .build());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedImage);
        return event.editReply().withFiles(MessageCreateFields.File.of("image.jpeg", byteArrayInputStream));
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
         /*
        Рабочий метод который берет картинку из интернета
        return event.reply(spec -> spec.addEmbed(embedCreateSpec -> embedCreateSpec.setImage("https://proslo.ru/wp-content/uploads/2024/02/china1.jpg")));
         */
        event.deferReply().subscribe();
        return methodThatTakesALongTime(event).then();
    }
}
