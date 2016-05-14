import javax.swing.*;

public class World extends JFrame{
	public World(){
		add(new Pacman());
		setTitle("VS Pacman");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(24* 28 + 24 *8, 24*32);
		setLocationRelativeTo(null);

		setVisible(true);
		setResizable(false);
	}

	public static void main(String[] args) {
		World world = new World();
		world.setVisible(true);
	}

}
