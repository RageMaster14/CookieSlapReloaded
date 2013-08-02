package rageteam.cookieslap.main;

import org.bukkit.plugin.java.JavaPlugin;

import rageteam.cookieslap.commands.HelpCommand;
import rageteam.cookieslap.managers.ConfigManager;
import rageteam.cookieslap.managers.PlayerManager;
import rageteam.cookieslap.objects.GameState;
import rageteam.cookieslap.util.Chat;
import rageteam.cookieslap.util.Logger;
import rageteam.cookieslap.util.Misc;

public class CS extends JavaPlugin{
	
	public GameState gameState = GameState.LOBBY;
	public boolean canStart = false;
	
	//Command Classes
	public HelpCommand cmdHelp;
	
	//Handler Classes
	
	//Manager Classes
	public PlayerManager playerManager;
	public ConfigManager cfgManager;
	
	//Util Classes
	public Chat chat;
	public Logger logger;
	public Misc misc;
	
	//Scoreboard
	public CookieSlapBoard csBoard;
	
	private void loadDependicies(){
		this.csBoard = new CookieSlapBoard(this);
		
		this.chat = new Chat(this);
		this.logger = new Logger(this);
		this.misc = new Misc(this);
		
		this.cmdHelp = new HelpCommand(this);
		
		this.playerManager = new PlayerManager(this);
		this.cfgManager = new ConfigManager(this);
	}
	
	private void loadHandlers(){
	}
	
	private void registerCommands(){
		getCommand("help").setExecutor(cmdHelp);
		
	}
	
	@Override
	public void onEnable(){
		loadDependicies();
		loadHandlers();
		registerCommands();
		
		csBoard.enable();
		
		logger.log(false, "Loading CS Dependicies");
	}
	
	@Override
	public void onDisable(){
		
	}

}
