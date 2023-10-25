package commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import russianroulettebot.Command;

import java.util.ArrayList;
import java.util.List;

public class Hbd implements Command {
    @Override
    public String getName() {
        return "hbd";
    }

    @Override
    public String getDescription() {
        return "Send a Happy Birthday message";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "who's birthday is it?", true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping o1 = event.getOption("user");
        User user = o1.getAsUser();
        event.reply(event.getUser().getAsMention() + " wishes you a happy birthday, " + user.getAsMention() + "!  🎂🎉").queue();
    }
}

