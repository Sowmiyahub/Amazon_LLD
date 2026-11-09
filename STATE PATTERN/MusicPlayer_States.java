
interface playerState{
    void play(musicPlayer player);
    void pause(musicPlayer player);
    void stop(musicPlayer player);
}
class playingState implements playerState{
    public void play(musicPlayer player){
        System.out.println("Music is Playing");
    }
    public void pause(musicPlayer player){
        System.out.println("Pausing music...");
        player.setState(new pausedState());
    }
    public void stop(musicPlayer player){
        System.out.println("Stopping music...");
        player.setState(new stoppedState());
    }
}
class pausedState implements playerState{
    public void play(musicPlayer player){
        System.out.println("Resuming Music...");
        player.setState(new playingState());
    }
    public void pause(musicPlayer player){
        System.out.println("Already Paused!");
    }
    public void stop(musicPlayer player){
        System.out.println("Stopping music...");
        player.setState(new stoppedState());
    }
}
class stoppedState implements playerState{
    public void play(musicPlayer player){
        System.out.println("Playing music...");
        player.setState(new playingState());
    }
    public void pause(musicPlayer player){
        System.out.println("Pausing music...");
        player.setState(new pausedState());
    }
    public void stop(musicPlayer player){
        System.out.println("Already Stopped");
    }
}
//context class
class musicPlayer{
    private playerState state;
    musicPlayer(){
        this.state = new stoppedState(); //Since we always want the player to start in the Stopped state
    }
    public void setState(playerState state){
        this.state = state; 
    }
    public void play(){
        state.play(this);
    }
    public void pause(){
        state.pause(this);
    }
    public void stop(){
        state.stop(this);
    }
}
public class Main{
    public static void main(String[] args){
        musicPlayer player = new musicPlayer();
        player.play();
        player.pause();
        player.stop();
        player.pause();
        player.stop();
        player.play();
        player.play();
        player.pause();
        player.stop();
        player.stop();
    }
}
