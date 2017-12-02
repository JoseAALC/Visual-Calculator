import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;



import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

// ---------------------------------------------------------------
// Classe que cria uma Frame principal, onde se situam os comandos
// de manipula��o de imagem. Implementa a interface ActionListener
// para lidar com os eventos produzidos pelos bot�es.
// ---------------------------------------------------------------
class VisualCalculator extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// Vari�veis globais de apoio
	// Aten��o: E se eu quiser ter m�ltiplas imagens?
	// Isto deve estar na classe ImageFrame!
	private Image image;
	private int sizex;
	private int sizey;;
	private int matrix[];
	ImagePanel imagePanel; // E se eu quiser m�ltiplas janelas?
	private Processor processor;
	private SegmentedFile segFile;
	// Fun��o main cria uma instance din�mica da classe
	public static void main(String args[])
	{
		new VisualCalculator();
	}

	// Construtor
	public VisualCalculator() {
		processor = new Processor();
		// Lidar com o evento de Fechar Janela
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Sinalizar que nao existe nenhum ImagePanel
		imagePanel = null;

		// Criar bot�es 
		this.setLayout(new GridLayout(4,1,1,1));

		Button button = new Button("Open File");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);		

		button = new Button("Apply Filter");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Apply Segmentation");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Remove Shadow");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Apply FloodFill");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);
		
		button = new Button("Guardar Resultado");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);

		button = new Button("Adicionar ao Dicion�rio");
		button.setVisible(true);
		button.addActionListener(this);
		add(button);

		pack();

		// Janela principal 	
		setLocation(30,100);
		setSize(200,200);
		setVisible(true);
	}


	// O utilizador carregou num bot�o
	public void actionPerformed (ActionEvent myEvent)
	{
		// Qual o bot�o premido?
		Button pressedButton = (Button)myEvent.getSource();
		String nomeBotao = pressedButton.getActionCommand();

		// Realizar ac��o adequada
		if (nomeBotao.equals("Open File")) openFile();
		else if (nomeBotao.equals("Apply Filter")) applyFilter();
		else if (nomeBotao.equals("Apply Segmentation")) applySegmentation();
		else if (nomeBotao.equals("Apply FloodFill")) applyFloodFill();
		else if (nomeBotao.equals("Remove Shadow")) removeShadows();
		else if (nomeBotao.equals("Guardar Resultado")) guardarResultado();
		else if (nomeBotao.equals("Adicionar ao Dicion�rio")) adicionarAoDicionario();
		
		
	}

	// Abrir um ficheiro de Imagem
	private void openFile() {
		LoadImage("cancela.jpg");

		sizex = image.getWidth(null);
		sizey = image.getHeight(null);
		matrix = new int[sizex*sizey];
		PixelGrabber pg = new PixelGrabber(image, 0, 0, sizex, sizey, matrix, 0, sizex);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
			System.err.println("interrupted waiting for pixels!");
			return;
		}
		if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
			System.err.println("image fetch aborted or errored");
			return;
		}

		// Visualizar imagem - Usar um Frame externo
		if (imagePanel==null) imagePanel = new ImagePanel(image);
		else imagePanel.newImage(image);
		imagePanel.setLocation(250, 5);
		imagePanel.setSize(image.getWidth(null),image.getHeight(null));
		imagePanel.setVisible(true);
	}

	public void applyFilter(){
		NoiseFilter noise = new NoiseFilter();
		int matrix[] = noise.medianFilter(10, 10, image);
		image = createImage(new MemoryImageSource(sizex, sizey, matrix, 0, sizex));
		// Carregar a imagem no painel externo de visualizacao
		imagePanel.newImage(image);
	}
	
	public void removeShadows() {
		segFile = processor.eliminateLateraShadow(segFile);
		image = createImage(new MemoryImageSource(sizex, sizey, segFile.getRepresentation(), 0, sizex));
		imagePanel.newImage(image);
	}
	
	public void applySegmentation(){
		segFile = processor.segment(image);
		image = createImage(new MemoryImageSource(sizex, sizey, segFile.getRepresentation(), 0, sizex));
		imagePanel.newImage(image);
	}
	
	public void applyFloodFill(){
		ArrayList<Blob> blobList = processor.floodFill(segFile.getBinaryMatrix());
		System.out.println(blobList.size());
		Collections.sort(blobList);
		blobList = processor.removeFalseBlobs(blobList);
		System.out.println(blobList.size());
		Blob blob = blobList.get(0);
		image = createImage(new MemoryImageSource(Math.abs(blob.getP2x()-blob.getP1x()), Math.abs(blob.getP2y()-blob.getP1y()), blob.getMatrixImage(), 0, Math.abs(blob.getP2x()-blob.getP1x()))); 
		imagePanel.newImage(image);
		
	}
	
	public void manipularImagem() {
		
	}

	// Fun��o de apoio que grava a imagem visualizada
	private void guardarResultado()
	{
		// Criar uma BufferedImage a partir de uma Image
		BufferedImage bi = new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics bg = bi.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();

		// Escrever ficheiro de sa�da
		// Pq n�o implementar uma interface de escolha do nome?
		try {
			ImageIO.write(bi, "jpg", new File("resultadoPraja.jpg"));
		} catch (IOException e) {
			System.err.println("Couldn't write output file!");
			return;
		}
	}

	private void adicionarAoDicionario(){

	}

	// Fun��o de apoio que carrega uma imagem externa
	public void LoadImage(String fileName) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		image = toolkit.getImage(fileName);
		MediaTracker mediaTracker = new MediaTracker(this);
		mediaTracker.addImage(image, 0);
		try { mediaTracker.waitForID(0); }
		catch (InterruptedException ie) {};		
	}
}
