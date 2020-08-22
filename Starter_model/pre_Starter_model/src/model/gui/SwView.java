package model.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import model.map.Edge;
import model.map.GridMapLoc;
import model.map.Light;
import model.map.Light.LIGHT;
import model.map.Loc;
import model.map.MapLoc;
import model.traffic.Traffic;

public class SwView {

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

	/************* Model's data ****************/

	public Traffic trafficData;
	public final int maxLocX = 10;
	public final int maxLocY = 10;

	/******************************************/

	public static void main(String[] args) {
		MapLoc map = new GridMapLoc(5, 5);
		Traffic traffic = new Traffic(map);
		SwView view = new SwView(800, 800, traffic);
//		traffic.run();
	}

	public SwView(int initViewx, int initViewy, Traffic tr) {

		/*********** Model Data *********/

		trafficData = tr;

		/*********** GUI ***************/

		frame = new JFrame("Traffic Simulator");
		frame.setPreferredSize(new Dimension(initViewx, initViewy));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		framePane = new JSplitPane();
		framePane.setEnabled(false);

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

		statePanel = new JPanel();

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
		edgePanelMap = new TreeMap<Edge, EdgePanel>();
		edges.setLayout(null);
		edges.setOpaque(false);
		for (Edge e : trafficData.map.getAllEdge()) {
			Loc from = e.getFrom(), to = e.getTo();
			int fromx = from.getX(), fromy = from.getY(), tox = to.getX(), toy = to.getY();
			JPanel p1 = verticesGrid[fromx][fromy], p2 = verticesGrid[tox][toy];
			EdgePanel edgePanel = new EdgePanel(getEdgePanelBounds(p1, p2));
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

	public MyLine getEdgePanelBounds(JPanel p1, JPanel p2) {
		Rectangle r1 = p1.getBounds(), r2 = p2.getBounds();
		int w1 = (int) r1.getWidth(), h1 = (int) r1.getHeight();
		int w2 = (int) r2.getWidth(), h2 = (int) r1.getHeight();
		// positive button radius
		int m1 = Math.min(w1, h1) / 4, m2 = Math.min(w2, h2) / 4;
		// line segments coordinates
		int cx1 = (int) r1.getCenterX(), cy1 = (int) r1.getCenterY();
		int cx2 = (int) r2.getCenterX(), cy2 = (int) r2.getCenterY();
		System.out.println("old c1");
		System.out.println(cx1);
		System.out.println(cy1);
		System.out.println("old c2");
		System.out.println(cx2);
		System.out.println(cy2);
		System.out.println();
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
		if (cx1 == cx2)
			cx2 += 2;
		if (cy1 == cy2)
			cy2 += 2;
		// return a smallest rectangle
		System.out.println("new c1");
		System.out.println(cx1);
		System.out.println(cy1);
		System.out.println("new c2");
		System.out.println(cx2);
		System.out.println(cy2);
		System.out.println();
		if (cx1 > cx2 && cy1 > cy2)
			return new MyLine(new Rectangle(cx2, cy2, cx1 - cx2, cy1 - cy2), true);
		if (cx1 > cx2 && cy1 < cy2)
			return new MyLine(new Rectangle(cx2, cy1, cx1 - cx2, cy2 - cy1), false);
		if (cx1 < cx2 && cy1 > cy2)
			return new MyLine(new Rectangle(cx1, cy2, cx2 - cx1, cy1 - cy2), false);
		if (cx1 < cx2 && cy1 < cy2)
			return new MyLine(new Rectangle(cx1, cy1, cx2 - cx1, cy2 - cy1), true);
		if (cx1 == cx2 && cy1 < cy2) 
			return new MyLine(new Rectangle(cx1, cy1, 0, cy2 - cy1), true);
		if (cx1 == cx2 && cy1 > cy2) 
			return new MyLine(new Rectangle(cx1, cy2, 0, cy1 - cy2), true);
		if (cx1 < cx2 && cy1 == cy2) 
			return new MyLine(new Rectangle(cx1, cy1, cx2 - cx1, 0), true);
		if (cx1 > cx2 && cy1 == cy2) 
			return new MyLine(new Rectangle(cx2, cy1, cx1 - cx2, 0), true);
		return new MyLine(null, true);
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
			g.fillOval(w / 2 - m, h / 2 - m, 2 * m, 2 * m);

			/******* draw the line on the g *******/

			if (!trafficData.map.isIntersect(loc))
				return;

			Loc hfrom = new Loc(loc.getX() - 1, loc.getY());
			Loc hto = new Loc(loc.getX() + 1, loc.getY());

			Light hori = trafficData.map.getLight(hfrom, loc, hto);
			g.setColor(Color.green);
			if (hori.getStatus() == LIGHT.GREEN) {
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
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}

	/************** EdgePanel *************/

	class EdgePanel extends JPanel {

		Edge edge;
		boolean direction; // true is left up, false is right up

		EdgePanel(MyLine line) {
			this.setLayout(null);
			this.setBounds(line.rectangle);
			this.setOpaque(false);
			this.direction = line.b;
		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.blue);
			if (direction)
				g.drawLine(0, 0, this.getWidth(), this.getHeight());
			else
				g.drawLine(0, this.getHeight(), this.getWidth(), 0);
			super.paintComponents(g);
		}
	}

	/*************** MapPanel ************/

	class MapPanel extends JPanel {

		MapPanel() {
			super();
			this.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent ev) {
					MapPanel mp = (MapPanel) ev.getSource();
					mp.remove(edges);
					initEdges();
					mp.add(edges, gbc);
					mp.setComponentZOrder(vertices, 1);
					mp.setComponentZOrder(edges, 0);
				}
			});
		}
	}

}
