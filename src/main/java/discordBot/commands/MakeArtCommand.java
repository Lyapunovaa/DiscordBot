package discordBot.commands;

import apiClient.utils.Properties;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateFields;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class MakeArtCommand implements SlashCommand {

    private static Mono<Message> methodThatTakesALongTime(ChatInputInteractionEvent event) {
        byte[] decodedImage = Base64.getDecoder().decode(Properties.properties.base64Image());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedImage);
        return event.createFollowup().withFiles(MessageCreateFields.File.of("image.jpeg", byteArrayInputStream));
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        byte[] decodedImage = Base64.getDecoder().decode(Properties.properties.base64Image());
        String base64pic = Properties.properties.base64Image();

        /*
        Рабочий метод который берет картинку из интернета
        return event.reply(spec -> spec.addEmbed(embedCreateSpec -> embedCreateSpec.setImage("https://proslo.ru/wp-content/uploads/2024/02/china1.jpg")));
         */

        return event.deferReply().withEphemeral(false).then(methodThatTakesALongTime(event).then());
    }
}
