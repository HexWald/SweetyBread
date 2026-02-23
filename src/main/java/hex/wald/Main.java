package hex.wald;

import hex.wald.listener.ComposterListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ComposterListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("sweetybread")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("sweetybread.reload")) {
                    sender.sendMessage("§cНет прав.");
                    return true;
                }

                reloadConfig();
                sender.sendMessage("§aSweetyBread: конфиг перезагружен.");
                return true;
            }

            sender.sendMessage("§eSweetyBread команды:");
            sender.sendMessage("§7/sweetybread reload §f- перезагрузить конфиг");
            return true;
        }

        return false;
    }
}