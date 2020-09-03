package model.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import model.map.Edge;
import model.map.GridMapLoc;
import model.map.Light;
import model.map.Light.LIGHT;
import model.map.Loc;
import model.map.MapLoc;
import model.traffic.Traffic;

public class SwView implements ViewInterface {

	/************* Swing Components ************/

	public JFrame frame;
	public JSplitPane framePane;
	public MapPanel mapPanel;
	public JPanel statePanel;
	public JPanel vertices;
	public JPanel edges;
	public JPanel[][] verticesGrid;
	public Map<Edge, EdgePanel> edgePanelMap;
	public GridBagConstraints gbc;
	public JButton step;
	public JButton run;
	public JButton pause;
	public JPanel controlPanel;
	public JTextArea textPanel;
	public JSplitPane statePane;
	public JLabel titlePanel;

	/************* Model's data ****************/

	public Traffic trafficData;
	public final int maxLocX = 12;
	public final int maxLocY = 12;

	/******************************************/

	public static void main(String[] args) {
		MapLoc map = new GridMapLoc(6, 6);
		Traffic traffic = new Traffic(map);
		SwView view = new SwView(1200, 800, traffic);
		traffic.view = view;
	}

	public SwView(int initViewx, int initViewy, Traffic tr) {

		/*********** Model Data *********/

		trafficData = tr;

		/*********** GUI ***************/

		frame = new JFrame("Traffic Simulator");
		frame.setPreferredSize(new Dimension(initViewx, initViewy));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		framePane = new JSplitPane();
//		framePane.setEnabled(false);

		mapPanel = new MapPanel();
		mapPanel.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		vertices = new JPanel();
		edges = new JPanel();

		mapPanel.add(vertices, gbc);

		step = new JButton("step");
		step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trafficData.paused = false;
				trafficData.step = true;
			}
		});
		run = new JButton("run");
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trafficData.paused = false;
				trafficData.step = false;
			}
		});
		pause = new JButton("pause");
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				trafficData.paused = true;
				trafficData.step = false;
			}
		});

		controlPanel = new JPanel();
		controlPanel.add(step);
		controlPanel.add(run);
		controlPanel.add(pause);

		titlePanel = new JLabel("Control Panel");

		textPanel = new JTextArea();

		statePanel = new JPanel();
		statePanel.setLayout(new GridLayout(3, 1));

//		statePanel.add(titlePanel, BorderLayout.PAGE_START);
		statePanel.add(controlPanel, BorderLayout.CENTER);
		statePanel.add(textPanel, BorderLayout.PAGE_END);

		framePane.setLeftComponent(mapPanel);
		framePane.setRightComponent(statePanel);

		initVertices();
		frame.repaint();

		frame.add(framePane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		initEdges();
		mapPanel.add(edges, gbc);

		mapPanel.setComponentZOrder(vertices, 1);
		mapPanel.setComponentZOrder(edges, 0);

		frame.revalidate();
		frame.pack();
		frame.repaint();
	}

	public void initVertices() {
		vertices.setLayout(new GridLayout(maxLocX, maxLocY));
		vertices.setOpaque(false);
		verticesGrid = new JPanel[maxLocX][maxLocY];
		for (int i = 0; i < maxLocX; i++) {
			for (int j = 0; j < maxLocY; j++) {
				JPanel tmp = new JPanel();
				tmp.setOpaque(false);
				tmp.setLayout(new GridLayout(1, 1));
				verticesGrid[i][j] = tmp;
				vertices.add(tmp);
			}
		}

		for (Loc loc : trafficData.map.getAllLoc()) {
			int x = loc.getX(), y = loc.getY();
			if (trafficData.map.isIntersect(loc)) {
				SwVertexButton vertex = new SwVertexButton(loc, Color.orange);
				verticesGrid[x][y].add(vertex);
			} else {
				SwVertexButton vertex = new SwVertexButton(loc, Color.gray);
				verticesGrid[x][y].add(vertex);
			}
		}

	}

	public void initEdges() {
		edges = new JPanel();
		Rectangle bound = mapPanel.getBounds();
		edgePanelMap = new TreeMap<Edge, EdgePanel>();
		edges.setLayout(null);
		edges.setOpaque(false);
		for (Edge e : trafficData.map.getAllEdge()) {
			Loc from = e.getFrom(), to = e.getTo();
			int fromx = from.getX(), fromy = from.getY(), tox = to.getX(), toy = to.getY();
			JPanel p1 = verticesGrid[fromx][fromy], p2 = verticesGrid[tox][toy];
			EdgePanel edgePanel = new EdgePanel(getEdgePanelLine(p1, p2), bound, e);
			edgePanel.addMouseListener(new SwEdgeListener());
			edgePanelMap.put(e, edgePanel);
			edges.add(edgePanel);
		}

	}

	/************ pair class ***************/

	class MyLine {
		public MyLine(Rectangle rectangle, boolean b) {
			this.rectangle = rectangle;
			this.b = b;
		}

		Rectangle rectangle;
		boolean b;
	}

	public Line2D getEdgePanelLine(JPanel p1, JPanel p2) {
		Rectangle r1 = p1.getBounds(), r2 = p2.getBounds();
//		System.out.println(r1);
		int w1 = (int) r1.getWidth(), h1 = (int) r1.getHeight();
		int w2 = (int) r2.getWidth(), h2 = (int) r1.getHeight();
		// positive button radius
		int m1 = Math.min(w1, h1) / 4, m2 = Math.min(w2, h2) / 4;
//		System.out.println("Line");
//		System.out.println(m1);
		// line segments coordinates
		int cx1 = (int) r1.getCenterX(), cy1 = (int) r1.getCenterY();
		int cx2 = (int) r2.getCenterX(), cy2 = (int) r2.getCenterY();
		// line segment lengths
		int dx = cx2 - cx1, dy = cy2 - cy1;
		double dz = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		// compute new line segments
		if (dz == 0)
			return null; // this should never happen
		int mx1 = (int) (m1 * dx / dz), my1 = (int) (m1 * dy / dz);
		int mx2 = (int) (m2 * dx / dz), my2 = (int) (m2 * dy / dz);
		cx1 += mx1;
		cy1 += my1;
		cx2 -= mx2;
		cy2 -= my2;
//		System.out.println("new c1");
//		System.out.println(cx1);
//		System.out.println(cy1);
//		System.out.println("new c2");
//		System.out.println(cx2);
//		System.out.println(cy2);
//		System.out.println();
		Point2D pt1 = new Point(cx1 - 2, cy1 - 2);
		Point2D pt2 = new Point(cx2 - 2, cy2 - 2);
		return new Line2D.Double(pt1, pt2);
	}

	/***************** Vertex Button *****************/

	class SwVertexButton extends JButton {

		Loc loc;

		SwVertexButton(Loc loc, Color color) {
			this.loc = loc;
			this.setBackground(color);
			this.addMouseListener(new SwVertexListener());
			this.setToolTipText(loc.toString());
		}

		@Override
		protected void paintComponent(Graphics g) {
			int w = this.getWidth();
			int h = this.getHeight();
			int m = Math.min(w, h) / 4;
			g.setColor(getBackground());
			int x = (int) (this.getBounds().getCenterX() - this.getBounds().getX());
			int y = (int) (this.getBounds().getCenterY() - this.getBounds().getY());
			g.fillOval(x - m, y - m, 2 * m, 2 * m);

			/******* draw the line on the g *******/

			if (!trafficData.map.isIntersect(loc))
				return;

			Loc hfrom = new Loc(loc.getX() - 1, loc.getY());
			Loc hto = new Loc(loc.getX() + 1, loc.getY());

			Light hori = trafficData.map.getLight(hfrom, loc, hto);
			g.setColor(Color.green);
			if (hori.getStatus() == LIGHT.RED) {
				g.drawLine(w / 2 - m, h / 2, w / 2 + m, h / 2);
			} else {
				g.drawLine(w / 2, h / 2 - m, w / 2, h / 2 + m);
			}
			super.paintComponents(g);
		}

		@Override
		public boolean contains(int x, int y) {
			int w = this.getWidth();
			int h = this.getHeight();
			int m = Math.min(w, h) / 4;
			Shape shape = new Ellipse2D.Float(w / 2 - m, h / 2 - m, 2 * m, 2 * m);
			return shape.contains(x, y);
		}
	}

	/************** SwvertexListener ******/

	class SwVertexListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			SwVertexButton svb = (SwVertexButton) e.getSource();
			trafficData.map.changeCrossStatus(svb.loc);
//			frame.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			SwVertexButton svb = (SwVertexButton) e.getSource();
			textPanel.setText(svb.loc.toString());
		}

		@Override
		public void mouseExited(MouseEvent e) {
			textPanel.setText("");
		}

	}

	/************** EdgePanel *************/

	class EdgePanel extends JPanel {

		Edge edge;
		Line2D line;

		EdgePanel(Line2D line, Rectangle bound, Edge edge) {
			this.setLayout(null);
			this.setBounds(bound);
			this.setOpaque(false);
			this.line = line;
			this.edge = edge;
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.blue);
			double perc = trafficData.getAllUsers(edge).size() / 30.0;
			g.drawLine((int) line.getX1(), (int) line.getY1(), (int) line.getX2(), (int) line.getY2());
			((Graphics2D) g).setStroke(new BasicStroke(4.0F));
			g.setColor(Color.red);
//			System.out.println(perc);
			if (perc != 0) {
				g.drawLine((int) line.getX1(), (int) line.getY1(),
						(int) (line.getX1() + perc * (line.getX2() - line.getX1())),
						(int) (line.getY1() + perc * (line.getY2() - line.getY1())));
			}
			g.setColor(Color.darkGray);
			g.drawString(Integer.toString(trafficData.getAllUsers(edge).size()) + "/30",
					(int) ((line.getX1() + line.getX2()) / 2), (int) ((line.getY1() + line.getY2()) / 2));

			super.paintComponents(g);
		}

		@Override
		public boolean contains(int x, int y) {
			Point2D p = new Point(x, y);
			return line.ptSegDist(p) <= 5;
		}
	}

	/************** SwEdgeListener ******/

	class SwEdgeListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			EdgePanel ep = (EdgePanel) e.getSource();
			textPanel.setText(ep.edge.toString());
		}

		@Override
		public void mouseExited(MouseEvent e) {
			textPanel.setText("");
		}

	}

	/*************** MapPanel ************/

	class MapPanel extends JPanel {

		MapPanel() {
			super();
			this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent ev) {
//					System.out.println("Yes");
					MapPanel mp = (MapPanel) ev.getSource();
					mp.remove(edges);
					initEdges();
					mp.add(edges, gbc);
					mp.setComponentZOrder(vertices, 1);
					mp.setComponentZOrder(edges, 0);
					frame.revalidate();
				}
			});
		}
	}

	@Override
	public void redraw(Traffic tr) {
		frame.repaint();

	}

}
