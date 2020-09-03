package model.gui;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.map.Edge;
import model.map.Light;
import model.map.Loc;
import model.map.Light.LIGHT;
import model.traffic.Traffic;

public class FXView extends Application implements ViewInterface {

	Stage initBox;

	public int maxX; // # cols
	public int maxY; // # rows

	public Traffic tr;

	BorderPane root = new BorderPane();
	AnchorPane rightPanel = new AnchorPane();
	GridPane controlPanel = new GridPane();
	StackPane board = new StackPane();
	Pane vertices = new Pane();
	public Pane edges = new Pane();

	Map<Loc, FXVertex> vertexMap = new TreeMap<Loc, FXVertex>();
	Map<Edge, FXEdge> edgeMap = new TreeMap<Edge, FXEdge>();

	@Override
	public void start(Stage primaryStage) {
		try {

			board.setPadding(new Insets(25, 0, 0, 25));

			board.setAlignment(Pos.CENTER);

			board.getChildren().addAll(vertices, edges);

			root.setCenter(board);
			root.setTop(myMenuBar(primaryStage));
			root.setRight(myControlPanel(primaryStage));
			Scene scene = new Scene(root, 1200, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Optimus Prime");
			primaryStage.setScene(scene);
			primaryStage.show();
			initBox();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	public void initBox() {
		try {
			// asking init information
			initBox = new Stage();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("InitBox.fxml"));
			Scene initScene;
			initScene = new Scene(loader.load());
			initScene.getStylesheets().add(getClass().getResource("initBoxStyle.css").toExternalForm());
			FXController controller = loader.getController();
			controller.view = this;

			initBox.setTitle("Init...");
			initBox.setScene(initScene);
			initBox.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeInit(Traffic tr) {
		initBox.close();
		this.tr = tr;

		this.drawVertices();
		this.drawEdges();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				tr.step();
			}
		};

		timer.start();
	}

	public AnchorPane myControlPanel(Stage primaryStage) {
		AnchorPane rightPanel = new AnchorPane();
		rightPanel.setMinWidth(300);
		rightPanel.setMaxWidth(300);

		GridPane runPane = new GridPane();
		AnchorPane.setTopAnchor(runPane, 50.0);
		AnchorPane.setLeftAnchor(runPane, 10.0);
		AnchorPane.setRightAnchor(runPane, 10.0);
		runPane.setMaxHeight(200);
		runPane.setMinHeight(200);

		Line div1 = new Line(0, 160, 300, 160);

		GridPane stepPane = new GridPane();
		AnchorPane.setTopAnchor(stepPane, 170.0);
		AnchorPane.setLeftAnchor(stepPane, 10.0);
		AnchorPane.setRightAnchor(stepPane, 10.0);
		stepPane.setMaxHeight(200);
		stepPane.setMinHeight(200);

		Line div2 = new Line(0, 230, 300, 230);

		FlowPane reportPane = new FlowPane();
		AnchorPane.setTopAnchor(reportPane, 250.0);
		AnchorPane.setLeftAnchor(reportPane, 10.0);
		AnchorPane.setRightAnchor(reportPane, 10.0);
		reportPane.setMinHeight(200);

		// add Concrete objects

		// runPane
		HBox runhb1 = new HBox();
		runhb1.setSpacing(10);
		Label speed = new Label("Speed");
		Slider slider = new Slider(0, 1, 0.5);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(0.25f);
		slider.setBlockIncrement(0.1f);
		runhb1.getChildren().addAll(speed, slider);

		HBox runhb2 = new HBox();
		runhb2.setSpacing(20);
		Button run = new Button("Run");
		Button pause = new Button("Pause");
		runhb2.getChildren().addAll(run, pause);

		runPane.add(runhb1, 0, 0);
		runPane.add(runhb2, 0, 1);

		// stepPane
		HBox stephb1 = new HBox();
		stephb1.setAlignment(Pos.CENTER_LEFT);
		stephb1.setSpacing(10);
		Label steps = new Label("#Steps:");
		TextField numsteps = new TextField("1");
		numsteps.setPrefWidth(50);
		Button step = new Button("Step");
		stephb1.getChildren().addAll(steps, numsteps, step);

		stepPane.add(stephb1, 0, 0);

		// reportPane
		FlowPane report = new FlowPane(Orientation.VERTICAL);
		report.setColumnHalignment(HPos.LEFT); // align labels on left
		report.setPrefWrapLength(200);
		Label title = new Label("Report");
		report.getChildren().add(title);

		reportPane.getChildren().add(report);

		rightPanel.getChildren().addAll(runPane, div1, stepPane, div2, reportPane);
		return rightPanel;
	}

	public MenuBar myMenuBar(Stage primaryStage) {
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());

		// File menu - new, save, print, and exit
		Menu fileMenu = new Menu("Game");
		MenuItem newMenuItem = new MenuItem("New");
		MenuItem saveMenuItem = new MenuItem("Save");
		MenuItem exitMenuItem = new MenuItem("Quit");

		// add the new menuitems to the fileMenu
		fileMenu.getItems().addAll(newMenuItem, saveMenuItem, exitMenuItem);

		Menu tutorialMenu = new Menu("Command");
		MenuItem keyBinding = new MenuItem("Key Binding");
		tutorialMenu.getItems().addAll(keyBinding);

		exitMenuItem.setOnAction(actionEvent -> Platform.exit());

		menuBar.getMenus().addAll(fileMenu, tutorialMenu);

		return menuBar;
	}

	@Override
	public void redraw(Traffic tr) {
		for (Edge e : tr.map.getAllEdge()) {
			edgeMap.get(e).edgeUpdate();
		}
	}

	public void drawVertices() {
		for (Loc loc : tr.map.getAllLoc()) {
			FXVertex v;
			if (tr.map.isIntersect(loc)) {
				v = new FXVertex(loc, Color.GRAY);
			} else {
				v = new FXVertex(loc, Color.BLACK);
			}
			vertexMap.put(loc, v);
			vertices.getChildren().add(v);
		}
		System.out.println("here");

	}

	public void drawEdges() {
		for (Edge e : tr.map.getAllEdge()) {
			FXEdge fxe = new FXEdge(e);
			edgeMap.put(e, fxe);
			edges.getChildren().addAll(fxe.full, fxe.part);
		}
	}

	class FXVertex extends Group {
		Loc loc;
		public Button b;
		Line l;

		FXVertex(Loc loc, Color color) {
			super();
			this.b = new Button();
			b.getStyleClass().add("vertices");

			NumberBinding dx = Bindings.divide(vertices.widthProperty(), maxX);
			NumberBinding dy = Bindings.divide(vertices.heightProperty(), maxY);

			b.layoutXProperty().bind(Bindings.multiply(dx, loc.getX()));
			b.layoutYProperty().bind(Bindings.multiply(dy, loc.getY()));

			NumberBinding minL = Bindings.min(vertices.heightProperty(), vertices.widthProperty());
			int minN = Math.max(maxX, maxY);
			NumberBinding r = Bindings.divide(minL, minN);
			NumberBinding d = Bindings.divide(r, 2);

			b.prefHeightProperty().bind(d);
			b.prefWidthProperty().bind(d);

			this.loc = loc;

			this.l = new Line();
			l.setStroke(Color.GREEN);
			l.setStrokeWidth(3);

			this.getChildren().addAll(b, l);

			this.lineUpdate();
		}

		void lineHori() {
			l.startXProperty().bind(Bindings.add(b.layoutXProperty(), 0));
			l.startYProperty().bind(Bindings.add(b.layoutYProperty(), Bindings.divide(b.heightProperty(), 2)));
			l.endXProperty().bind(Bindings.add(b.layoutXProperty(), b.widthProperty()));
			l.endYProperty().bind(Bindings.add(b.layoutYProperty(), Bindings.divide(b.heightProperty(), 2)));
		}

		void lineVert() {
			l.startXProperty().bind(Bindings.add(b.layoutXProperty(), Bindings.divide(b.widthProperty(), 2)));
			l.startYProperty().bind(Bindings.add(b.layoutYProperty(), 0));
			l.endXProperty().bind(Bindings.add(b.layoutXProperty(), Bindings.divide(b.widthProperty(), 2)));
			l.endYProperty().bind(Bindings.add(b.layoutYProperty(), b.heightProperty()));
		}

		void lineUpdate() {
			if (!tr.map.isIntersect(loc)) {
				return;
			}

			Loc hfrom = new Loc(loc.getX() - 1, loc.getY());
			Loc hto = new Loc(loc.getX() + 1, loc.getY());

			Light hori = tr.map.getLight(hfrom, loc, hto);

			if (hori.getStatus() == LIGHT.GREEN) {
				lineHori();
			} else {
				lineVert();
			}
		}

		ReadOnlyDoubleProperty widthProperty() {
			return b.widthProperty();
		}

		ReadOnlyDoubleProperty heightProperty() {
			return b.heightProperty();
		}

	}

	class FXEdge {

		Line full = new Line();
		Line part = new Line();

		Edge e;

		FXEdge(Edge e) {
			super();

			this.e = e;

			FXVertex v1 = vertexMap.get(e.getFrom());
			FXVertex v2 = vertexMap.get(e.getTo());
			NumberBinding startX = Bindings.add(v1.b.layoutXProperty(), Bindings.divide(v1.b.widthProperty(), 2));
			NumberBinding startY = Bindings.add(v1.b.layoutYProperty(), Bindings.divide(v1.b.heightProperty(), 2));
			NumberBinding endX = Bindings.add(v2.b.layoutXProperty(), Bindings.divide(v2.b.widthProperty(), 2));
			NumberBinding endY = Bindings.add(v2.b.layoutYProperty(), Bindings.divide(v2.b.heightProperty(), 2));

			if (startX.getValue().doubleValue() == endX.getValue().doubleValue()) {
				startY = Bindings.add(startY, Bindings.divide(v1.b.heightProperty(), 2));
				endY = Bindings.subtract(endY, Bindings.divide(v2.b.heightProperty(), 2));
			} else {
				startX = Bindings.add(startX, Bindings.divide(v1.b.widthProperty(), 2));
				endX = Bindings.subtract(endX, Bindings.divide(v2.b.widthProperty(), 2));
			}
			
			full.startXProperty().bind(startX);
			full.startYProperty().bind(startY);
			full.endXProperty().bind(endX);
			full.endYProperty().bind(endY);
			full.setStroke(Color.LIGHTBLUE);
			full.setStrokeWidth(3);
			
			part.setStroke(Color.RED);
			part.setStrokeWidth(3);
			part.startXProperty().bind(full.startXProperty());
			part.startYProperty().bind(full.startYProperty());
			
			edgeUpdate();
		}

		void edgeUpdate() {
			int usernum = tr.getAllUsers(e).size();
			System.out.println(usernum);
			double perc = usernum / 30.0;

			NumberBinding dx = Bindings.subtract(full.endXProperty(), full.startXProperty());
			NumberBinding dy = Bindings.subtract(full.endYProperty(), full.startYProperty());

			NumberBinding ax = Bindings.multiply(perc, dx);
			NumberBinding ay = Bindings.multiply(perc, dy);

			part.endXProperty().bind(Bindings.add(ax, full.startXProperty()));
			part.endYProperty().bind(Bindings.add(ay, full.startYProperty()));

		}
	}

}
