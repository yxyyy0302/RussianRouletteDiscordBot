package russianroulettebot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {
    private static List<Command> commands = new ArrayList<>();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            for (Command command : commands) {
                if (command.getOptions() == null) {
                    guild.upsertCommand(command.getName(), command.getDescription()).setGuildOnly(true).queue();
                } else {
                    guild.upsertCommand(command.getName(), command.getDescription())
                            .addOptions(command.getOptions()).queue();
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        for (Command command : commands) {
            if (command.getName().equals(event.getName())) {
                command.execute(event);
                return;
            }
        }
    }

    public static List<Command> getCommands() {
        return commands;
    }

    public void add(Command command) {
        commands.add(command);
    }
}
