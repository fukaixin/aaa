import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



public class FileHandler {
	private CirclesFrame mainFrame;
	private CirclesPanel canvas;
	
	public FileHandler(CirclesFrame mainFrame,CirclesPanel canvas){
		this.mainFrame=mainFrame;
		this.canvas=canvas;
	}
	
	
	public void openFile() {
		 JFileChooser filechooser = new JFileChooser();
		 filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    int returnVal = filechooser.showOpenDialog(mainFrame);
		    
		    if(returnVal == JFileChooser.CANCEL_OPTION) {
		       return;
		    }
		    File fileName = filechooser.getSelectedFile();
		    fileName.canRead();
		    if(fileName == null || fileName.getName().equals(""))
		    {
		    	JOptionPane.showMessageDialog(filechooser,"文件名不存在","请输入文件名！",JOptionPane.ERROR_MESSAGE);
		    }
		    
		    else {
		    	
					try {
						FileInputStream ifs = new FileInputStream(fileName);
						ObjectInputStream input = new ObjectInputStream(ifs);
						
						int countNumber = 0;
						Ellipse2D inputRecord;
						Color inputColor;
						countNumber = input.readInt();
						for(int i =0;i<countNumber/2;i++)
						{
							inputRecord = (Ellipse2D)input.readObject();
							canvas.circles.add(inputRecord);
						}
						for(int i =0;i<countNumber/2;i++)
						{
							inputColor = (Color)input.readObject();
							canvas.colors.add(inputColor);
						}
						input.close();
						canvas.repaint();
					} catch (Exception e) {
						JOptionPane.showMessageDialog(mainFrame,"文件打开失败","打开文件时出错",JOptionPane.ERROR_MESSAGE);
					} 
		    }//else end
	}//open file end
	
	public void saveFile() {
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = filechooser.showSaveDialog(mainFrame);
		if(result == JFileChooser.CANCEL_OPTION){
        	return ;
        }
        
        File fileName = filechooser.getSelectedFile();
	    fileName.canWrite();
	    if(fileName == null || fileName.getName().equals(""))
	    {
	    	JOptionPane.showMessageDialog(filechooser,"文件名","请输入文件名！",JOptionPane.ERROR_MESSAGE);
	    }
	    else {
	    	try {
				fileName.delete();
				FileOutputStream fos = new FileOutputStream(fileName+".data");
				ObjectOutputStream output = new ObjectOutputStream(fos);
				output.writeInt(canvas.circles.size()+canvas.colors.size());
				int i;
				for(i = 0;i<canvas.circles.size();i++)
				{
					Ellipse2D p = (Ellipse2D) canvas.circles.get(i);
					output.writeObject(p);
					output.flush();  
				}
				for(int m = 0;i<(canvas.circles.size()+canvas.colors.size());i++)
				{
					Color p =  (Color) canvas.colors.get(m);
					output.writeObject(p);
					output.flush();  
					m++;
				}
				output.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }//else end
	}//save file end

}
