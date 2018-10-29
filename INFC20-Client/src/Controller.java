public class Controller {
	
	private WebService service = new WebService(this);
	GUI gui = new GUI(this);
	
	public void showError(String errorMsg) {
		gui.showError(errorMsg);
	}
}
