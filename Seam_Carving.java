import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Seam_Carving
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		if (args.length == 3)
		{
			int vert_pass=Integer.parseInt(args[1]);
			int hort_pass=Integer.parseInt(args[2]);			
			
			
////////////////VERTICAL SEAM///////////////////////////VERTICAL SEAM////////////////VERTICAL SEAM////////////////////VERTICAL SEAM			
		// removing vertical seams
		for(int k=0;k<vert_pass;k++)
		{
		// Read the .pgm file as input
			Scanner sc = new Scanner(new FileReader(args[0]));
			sc.nextLine();
			sc.nextLine();
			int columns = sc.nextInt();
			int rows = sc.nextInt();
			int max_pixel = sc.nextInt();
//***********************************************************************************************************//
			// Create a 2D array & allocate memory
			int[][] array_2D = new int[rows][columns];

			System.out.println("*******Original Matrix***********");
			// now read all the pixels into a 2D array
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++) 
				{
					array_2D[i][j] = sc.nextInt();
					System.out.print(array_2D[i][j]+" ");
				}
				System.out.println();
			}

			sc.close();
//*****************************************************************************************************************//				
			// Create a 2D array to store the ENERGY of pixels
			
			System.out.println("********Energy Matrix*******");
			int [][]energy_2D=new int[rows][columns];
			
			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < columns; j++) 
				{
					if(i==0&&j==0)
					{
						energy_2D[i][j]= Math.abs(array_2D[i][j]-array_2D[i+1][j])+     // Bottom
						                 Math.abs(array_2D[i][j]-array_2D[i][j+1]);    // Right
					//	System.out.print(energy_2D[i][j]+" ");
					}
					
					else if(i==0&&j==columns-1)
					{
						energy_2D[i][j]= Math.abs(array_2D[i][j]-array_2D[i+1][j])+     // Bottom
								         Math.abs(array_2D[i][j]-array_2D[i][j-1]);     // Left
					//	System.out.print(energy_2D[i][j]+" ");
					}
					
					else if(i==rows-1 && j==0)
					{
						energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
									    Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right      
					//	System.out.print(energy_2D[i][j]+" ");
					}
					
					else if(i==rows-1 && j==columns-1)
					{
						energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
										Math.abs(array_2D[i][j]-array_2D[i][j-1]);     // Left
					//	System.out.print(energy_2D[i][j]+" ");
										
					}
					else if(i==0 && j<columns-1)
					{
						energy_2D[i][j]= Math.abs(array_2D[i][j]-array_2D[i+1][j])+	    // Bottom
										 Math.abs(array_2D[i][j]-array_2D[i][j-1])+      // Left
										 Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right
					//	System.out.print(energy_2D[i][j]+" ");
					}
					
					else if(i<rows-1 && j==0)
					{
						energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
						                Math.abs(array_2D[i][j]-array_2D[i+1][j])+	    // Bottom
						                Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right
					//	System.out.print(energy_2D[i][j]+" ");
					}
					else if(i==rows-1 && j<columns-1)
					{
						energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
						                Math.abs(array_2D[i][j]-array_2D[i][j-1])+      // Left
						                Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right
					//	System.out.print(energy_2D[i][j]+" ");
					}
					
					else if(i<rows-1 && j==columns-1)
					{
						energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
								 		Math.abs(array_2D[i][j]-array_2D[i+1][j])+		// Bottom
						                Math.abs(array_2D[i][j]-array_2D[i][j-1]);     // Left
					//	System.out.print(energy_2D[i][j]+" ");							                
					}
					
					else
					{
					
					    energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
							            Math.abs(array_2D[i][j]-array_2D[i+1][j])+	    // Bottom
							            Math.abs(array_2D[i][j]-array_2D[i][j-1])+      // Left
							            Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right      
					//	System.out.print(energy_2D[i][j]+" ");
					}				
					
				}
				System.out.println();
			}
			
//***************************************************************************************************************//				
		// Calculate a Cumulative minimum energy M-array 2D	
			
			System.out.println("Cumulative Energy Matrix");
			int [][]cumulative_M_min_energy_Total=new int[rows][columns];
			
			for(int i=0;i<rows;i++)
			{
				for(int j=0;j<columns;j++)
				{
					if(i==0 && j<columns)
					{
						cumulative_M_min_energy_Total[i][j]=energy_2D[i][j];
					//	System.out.print(energy_2D[i][j]+" ");
					}
					else if(j==0 && i>0 )
					{
						cumulative_M_min_energy_Total[i][j]=energy_2D[i][j]+ Min_of_three(99999,
																						  cumulative_M_min_energy_Total[i-1][j],
								                                                          cumulative_M_min_energy_Total[i-1][j+1]);
					//	System.out.print(cumulative_M_min_energy_Total[i][j]+" ");
					}
					
					else if(j==columns-1)
					{
						cumulative_M_min_energy_Total[i][j]=energy_2D[i][j]+ Min_of_three(cumulative_M_min_energy_Total[i-1][j-1],
																						  cumulative_M_min_energy_Total[i-1][j],
								                                                               999999);
					//	System.out.print(cumulative_M_min_energy_Total[i][j]+" ");
					}			
					
					else
					{
					
						cumulative_M_min_energy_Total[i][j]=energy_2D[i][j]+ Min_of_three(cumulative_M_min_energy_Total[i-1][j-1],
																						  cumulative_M_min_energy_Total[i-1][j],
																						  cumulative_M_min_energy_Total[i-1][j+1]);
					//	System.out.print(cumulative_M_min_energy_Total[i][j]+" ");
					}
					
				}
				System.out.println();
			}
			
			
//************************************************** **************************************************************//
			// Finding the least pass...from bottom to top
			
		
		int min_pos=Minimum_of_Bottom_ROW(cumulative_M_min_energy_Total[rows-1]);
		System.out.println("min position is @"+(rows-1)+","+min_pos+" in the bottom row ");				
			
	    cumulative_M_min_energy_Total=find_vertical_pass(rows,columns,min_pos,cumulative_M_min_energy_Total);
	    
	    System.out.println(" after -999");
	    
	    for(int i=0;i<rows;i++)
	    {
	    	for(int j=0;j<columns;j++)
	    	{
	    		System.out.print(cumulative_M_min_energy_Total[i][j]+"  ");
	    	}
	    	System.out.println();
	    }
//********************************************************************************************************************//					
	  // using ArrayList<ArrayList> datastructure to be able to dynamically delete elements
	    
	   
	    ArrayList<ArrayList<Integer>> completer_struc=new ArrayList<ArrayList<Integer>>();
	   
	    
	    for(int i=0;i<rows;i++)
	    {
	      ArrayList<Integer> inner_ele=new ArrayList<Integer>();
	    	for(int j=0;j<columns;j++)
	    	{
	    		inner_ele.add(array_2D[i][j]);
	    	}
	    	completer_struc.add(inner_ele);
	    }		  
	    
	    System.out.println(completer_struc);
//*********************************************************************************************************************//
	  // iterate over arraylist of arraylist and search for element -999 in cumulative matrix and delete corresponding ele in arry2D
	    
	    ArrayList<Integer> rowList=new ArrayList<Integer>();
	    
	    for(int i=0;i<rows;i++)
	    {
	    	for(int j=0;j<columns;j++)
	    	{
	    		rowList=completer_struc.get(i);
	    		
	    		if(cumulative_M_min_energy_Total[i][j]==-999)
	    		{
	    			rowList.remove(j);
	    		}
	    		
	    		completer_struc.remove(i);
	    		completer_struc.add(i, rowList);
	    	}
	    }
	    
	    //completer_struc contains list of all elements after deletion of a  vertical pass
	    System.out.print(completer_struc);
	    
//***************************************************************************************************************************//		
	    // convert the completer_struc to 2D array and write to a .pgm file
	    
	    PrintWriter pw = new PrintWriter(args[0]);
		pw.println("P2");
		pw.println("# comment line");
		pw.print(columns-1 + " ");
		pw.println(rows);
		pw.println(255);
		for (int i = 0; i <rows ; i++) 
		{
			for (int j = 0; j <columns-1 ; j++)
			{
				pw.print(completer_struc.get(i).get(j) + " ");

			}
			pw.print("\n");
		}

		pw.close();    
	    
		}// iterating for n vertical seams
			
		
/////////////HORIZONTAL SEAM//////////////////HORIZONTAL SEAM/////////////////////HORIZONTAL SEAM/////////////////////////////			
			
			for(int k=0;k<hort_pass;k++)
			{
			//	Read the .pgm file as input
				Scanner sc = new Scanner(new FileReader(args[0]));
				sc.nextLine();
				sc.nextLine();
				int columns = sc.nextInt();
				int rows = sc.nextInt();
				int max_pixel = sc.nextInt();
//***********************************************************************************************************//
				// Create a 2D array & allocate memory
				int[][] array_2D = new int[rows][columns];

				System.out.println("*******Original Matrix***********");
				// now read all the pixels into a 2D array
				for (int i = 0; i < rows; i++)
				{
					for (int j = 0; j < columns; j++) 
					{
						array_2D[i][j] = sc.nextInt();
						System.out.print(array_2D[i][j]+" ");
					}
					System.out.println();
				}

				sc.close();
//*****************************************************************************************************************//				
				// Create a 2D array to store the ENERGY of pixels
				
				System.out.println("********Energy Matrix*******");
				int [][]energy_2D=new int[rows][columns];
				
				for (int i = 0; i < rows; i++)
				{
					for (int j = 0; j < columns; j++) 
					{
						if(i==0&&j==0)
						{
							energy_2D[i][j]= Math.abs(array_2D[i][j]-array_2D[i+1][j])+     // Bottom
							                 Math.abs(array_2D[i][j]-array_2D[i][j+1]);    // Right
							System.out.print(energy_2D[i][j]+" ");
						}
						
						else if(i==0&&j==columns-1)
						{
							energy_2D[i][j]= Math.abs(array_2D[i][j]-array_2D[i+1][j])+     // Bottom
									         Math.abs(array_2D[i][j]-array_2D[i][j-1]);     // Left
							System.out.print(energy_2D[i][j]+" ");
						}
						
						else if(i==rows-1 && j==0)
						{
							energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
										    Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right      
							System.out.print(energy_2D[i][j]+" ");
						}
						
						else if(i==rows-1 && j==columns-1)
						{
							energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
											Math.abs(array_2D[i][j]-array_2D[i][j-1]);     // Left
							System.out.print(energy_2D[i][j]+" ");
											
						}
						else if(i==0 && j<columns-1)
						{
							energy_2D[i][j]= Math.abs(array_2D[i][j]-array_2D[i+1][j])+	    // Bottom
											 Math.abs(array_2D[i][j]-array_2D[i][j-1])+      // Left
											 Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right
							System.out.print(energy_2D[i][j]+" ");
						}
						
						else if(i<rows-1 && j==0)
						{
							energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
							                Math.abs(array_2D[i][j]-array_2D[i+1][j])+	    // Bottom
							                Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right
							System.out.print(energy_2D[i][j]+" ");
						}
						else if(i==rows-1 && j<columns-1)
						{
							energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
							                Math.abs(array_2D[i][j]-array_2D[i][j-1])+      // Left
							                Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right
							System.out.print(energy_2D[i][j]+" ");
						}
						
						else if(i<rows-1 && j==columns-1)
						{
							energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
									 		Math.abs(array_2D[i][j]-array_2D[i+1][j])+		// Bottom
							                Math.abs(array_2D[i][j]-array_2D[i][j-1]);     // Left
							System.out.print(energy_2D[i][j]+" ");							                
						}
						
						else
						{
						
						    energy_2D[i][j]=Math.abs(array_2D[i][j]-array_2D[i-1][j])+      // TOP
								            Math.abs(array_2D[i][j]-array_2D[i+1][j])+	    // Bottom
								            Math.abs(array_2D[i][j]-array_2D[i][j-1])+      // Left
								            Math.abs(array_2D[i][j]-array_2D[i][j+1]);      // Right      
							System.out.print(energy_2D[i][j]+" ");
						}				
						
					}
					System.out.println();
				}
				
//***************************************************************************************************************//				
			// Calculate a Cumulative minimum energy M-array 2D	
				
				System.out.println("Cumulative Energy Matrix");
				int [][]cumulative_M_min_energy_Total=new int[columns][rows];
				
				for(int i=0;i<columns;i++)
				{
					for(int j=0;j<rows;j++)
					{
						if(i==0 && j<rows)
						{
							cumulative_M_min_energy_Total[i][j]=energy_2D[j][i];
							System.out.print(energy_2D[j][i]+" ");
						}
						else if(j==0 && i>0 )
						{
							cumulative_M_min_energy_Total[i][j]=energy_2D[j][i]+ Min_of_three(99999,
																							  cumulative_M_min_energy_Total[i-1][j],
																							  cumulative_M_min_energy_Total[i-1][j+1]);
							System.out.print(cumulative_M_min_energy_Total[i][j]+" ");
						}
						
						else if(j==rows-1)
						{
							cumulative_M_min_energy_Total[i][j]=energy_2D[j][i]+ Min_of_three(cumulative_M_min_energy_Total[i-1][j-1],
																							  cumulative_M_min_energy_Total[i-1][j],
									                                                               999999);
							System.out.print(cumulative_M_min_energy_Total[i][j]+" ");
						}			
						
						else
						{
						
							cumulative_M_min_energy_Total[i][j]=energy_2D[j][i]+ Min_of_three(cumulative_M_min_energy_Total[i-1][j-1],
																							  cumulative_M_min_energy_Total[i-1][j],
																							  cumulative_M_min_energy_Total[i-1][j+1]);
							System.out.print(cumulative_M_min_energy_Total[i][j]+" ");
						}
						
					}
					System.out.println();
				}
							
				
				
				int min_pos=Minimum_of_Bottom_ROW(cumulative_M_min_energy_Total[columns-1]);
				System.out.println("min position is @"+(columns-1)+","+min_pos+" in the bottom row ");				
					
				  cumulative_M_min_energy_Total=find_vertical_pass(columns,rows,min_pos,cumulative_M_min_energy_Total);
				    
				    System.out.println(" after -999");
				    
				    for(int i=0;i<columns;i++)
				    {
				    	for(int j=0;j<rows;j++)
				    	{
				    		System.out.print(cumulative_M_min_energy_Total[i][j]+"  ");
				    	}
				    	System.out.println();
				    }
			
				   			    
				    
				    
				    ArrayList<ArrayList<Integer>> completer_struc=new ArrayList<ArrayList<Integer>>();
				   
				    
				  for(int i=0;i<columns;i++)
				    {
				      ArrayList<Integer> inner_ele=new ArrayList<Integer>();
				    	for(int j=0;j<rows;j++)
				    	{
				    		inner_ele.add(array_2D[j][i]);
				    	}
				    	completer_struc.add(inner_ele);
				    }		  
				    
				    System.out.println(completer_struc);
				    
				    
				    
				    ArrayList<Integer> rowList=new ArrayList<Integer>();
				    
				    for(int i=0;i<columns;i++)
				    {
				    	for(int j=0;j<rows;j++)
				    	{
				    		rowList=completer_struc.get(i);
				    		
				    		if(cumulative_M_min_energy_Total[i][j]==-999)
				    		{
				    			rowList.remove(j);
				    		}
				    		
				    		completer_struc.remove(i);
				    		completer_struc.add(i, rowList);
				    	}
				    }
				    
				    //completer_struc contains list of all elements after deletion of a  vertical pass
				    System.out.print(completer_struc);
				    
				    
		    
		    //completer_struc contains list of all elements after deletion of a  vertical pass
	//	    System.out.print(completer_struc);
		    // now we need to transpose back the complete structure again back to original form..
		    // convert the complete struc and transpose
		    
		    int original_array_back_after_hort_pass [][]=new int[rows][columns];
		    
		    System.out.println("just before write   original_array_back_after_hort_pass[j][i]");
		    for(int i=0;i<columns;i++)
		    {
		    	for(int j=0;j<rows-1;j++)
		    	{
		    		original_array_back_after_hort_pass[j][i]=completer_struc.get(i).get(j);
		    		System.out.print(original_array_back_after_hort_pass[j][i]+" ");
		    	}
		    	System.out.println();
		    }
		    
		    
		    
//***************************************************************************************************************************	
		    // convert the completer_struc to 2D array and write to a .pgm file
	    
		    PrintWriter pw = new PrintWriter(args[0]);
			pw.println("P2");
			pw.println("# comment line");
			pw.print(columns + " ");
			pw.println(rows-1);
			pw.println(255);
			for (int i = 0; i < rows-1; i++) 
			{
				for (int j = 0; j < columns; j++)
				{
					pw.print(original_array_back_after_hort_pass[i][j] + " ");

				}
				pw.print("\n");
			}

			pw.close();     
			    
		    }// iterating for n horizontal seams
				

			
			} 
			
		else
			System.out.println("Enter the correct number of arguments(4)");

	}// main end

//*********************************************************************************************************************//	
	private static int[][] find_vertical_pass(int rows, int columns, int min_pos,int [][]cumulative_M_min_energy_Total) {
		
		int min_ele=0;
		for(;rows>0;rows--)
		{	
			if(min_pos==0)
			{
			   min_ele=Min_of_three(99999,
			             		    cumulative_M_min_energy_Total[rows-1][min_pos],
			             		    cumulative_M_min_energy_Total[rows-1][min_pos+1]);
			   min_pos=find_which_element_vert(min_ele,min_pos,cumulative_M_min_energy_Total,rows);
			}
			else if(min_pos==columns-1)
			{
				min_ele=Min_of_three(cumulative_M_min_energy_Total[rows-1][min_pos-1],
			             		     cumulative_M_min_energy_Total[rows-1][min_pos],
					                 99999);
				min_pos=find_which_element_vert(min_ele,min_pos,cumulative_M_min_energy_Total,rows);
			}
			
			else
			{
				min_ele=Min_of_three(cumulative_M_min_energy_Total[rows-1][min_pos-1],
									 cumulative_M_min_energy_Total[rows-1][min_pos],
					                 cumulative_M_min_energy_Total[rows-1][min_pos+1]);	
				min_pos=find_which_element_vert(min_ele,min_pos,cumulative_M_min_energy_Total,rows);
			}	
		
		}	                   
		
		return cumulative_M_min_energy_Total;
		
	}

	




	private static int find_which_element_vert(int min_ele, int min_pos, int[][] cumulative_M_min_energy_Total,int rows)
	{
		int row_x,column_y=0;
		
		if(min_pos==0)
		{
			if(min_ele==cumulative_M_min_energy_Total[rows-1][min_pos])
			{
				row_x=rows-1;
				column_y=min_pos;
				cumulative_M_min_energy_Total[row_x][column_y]=-999;
			
			}
			else if(min_ele==cumulative_M_min_energy_Total[rows-1][min_pos+1])
			{
				row_x=rows-1;
				column_y=min_pos+1;
				cumulative_M_min_energy_Total[row_x][column_y]=-999;
			}
		}
		else if(min_ele==cumulative_M_min_energy_Total[rows-1][min_pos-1])
		{
			row_x=rows-1;
			column_y=min_pos-1;
			cumulative_M_min_energy_Total[row_x][column_y]=-999;
			
		}
		else if(min_ele==cumulative_M_min_energy_Total[rows-1][min_pos])
		{
			row_x=rows-1;
			column_y=min_pos;
			cumulative_M_min_energy_Total[row_x][column_y]=-999;
		}
		else if(min_ele==cumulative_M_min_energy_Total[rows-1][min_pos+1])
		{
			row_x=rows-1;
			column_y=min_pos+1;
			cumulative_M_min_energy_Total[row_x][column_y]=-999;
		}    
		min_pos=column_y;
		return min_pos;
		
	}
//***********************************************************************************************************************
	

	private static int Minimum_of_Bottom_ROW(int Bottom_row[])
	{
		int min_num=Bottom_row[0];
		int min_at_pos=0;		
		for(int i=0;i<Bottom_row.length;i++)
		{
			if(Bottom_row[i]< min_num)				// less than <...we need the first smallest element if tie
			{
				min_num=Bottom_row[i];
				min_at_pos=i;
			}
		}	
		return min_at_pos;
	}
	
	

	private static int Min_of_three(int a, int b, int c) 
	{
		if(a>b)
		{
			if(b>c)
				return c;
			else
				return b;				
		}
		else
		{
			if(a>c)
				return c;
			else
				return a;
		}
				
	}

}// class end
