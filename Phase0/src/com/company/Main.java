package com.company;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);
        String nstr= scanner.nextLine();
        int n = Integer.parseInt(nstr);
        double[][] matrix = new double[n][n];
        String[] matrixStrs = new String[n];
        for (int i = 0; i < n; i++) {
            matrixStrs[i]=scanner.nextLine();
            String[] splited = matrixStrs[i].split(" ");
            for (int j = 0; j < splited.length; j++) {
                matrix[i][j]=Double.parseDouble(splited[j]);
            }
        }
        String bStr= scanner.nextLine();
        String []bSplited = bStr.split(" ");
        double [] bMat = new double[n];
        for (int i = 0; i < bSplited.length; i++) {
            bMat[i]=Double.parseDouble(bSplited[i]);
        }
        //------------------------------------------------
        double [][] augmentedMatrix = new double[n][n+1];
        int z=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n+1; j++) {
                if(j!=n)
                {
                    augmentedMatrix[i][j]=matrix[i][j];
                }
                else
                {
                    augmentedMatrix[i][j]=bMat[z];
                    z++;
                }
            }
        }
        //-------------------------------------------
        printMatrix(augmentedMatrix,n);
        System.out.println("-----------------------------------------------");
        allPivots(augmentedMatrix,n);
        allReductions(augmentedMatrix,n);

    }
    public static void createPivot(double arr[][],int startCol,int startRow,int n)
    {
        if(startCol!=n-1)
        {
            double help = arr[startRow][startCol];
            int temp =  startRow;
            while (help==0)
            {
                temp++;
                help = arr[temp][startCol];
            }
            interChange(arr,startRow,temp);
            for (int i = startRow+1; i <n ; i++) {
                if(arr[i][startCol]!=0)
                {
                    replacement(arr,i,startRow,((arr[i][startCol]*-1)/arr[startRow][startCol]));
                }
            }
            printMatrix(arr,n);
            System.out.println("*********************************************");
        }
    }
    public static void allPivots(double arr[][],int n)
    {
        for (int i = 0; i <n ; i++) {
            createPivot(arr,i,i,n);
        }
    }
    public static void reduction(double arr[][],int startCol,int startRow,int n)
    {
            for (int i = startRow-1; i >= 0 ; i--) {
                if(arr[i][startCol]!=0.0)
                {
                    replacement(arr,i,startRow,(arr[i][startCol]*-1)/arr[startRow][startCol]);
                }
            }

    }
    public static void allReductions(double arr[][],int n)
    {
        for (int i = 0; i < n; i++) {
            reduction(arr,i,i,n);
            scaling(arr,1.0/(arr[i][i]),i);
            printMatrix(arr,n);
            System.out.println("*********************************************");
        }
        roundingBs(arr,n);
        System.out.println("*********************************************");
        printMatrix(arr,n);
    }
    public static void roundingBs(double arr[][],int n)
    {
        for (int i = 0; i < n; i++) {
          arr[i][n] = Math.round(arr[i][n]*10.0)/10.0;
        }
    }
    public static void interChange(double arr[][],int r1,int r2)
    {
        if(r1!=r2)
        {
            double []temp= arr[r1].clone();
            for (int i = 0; i < arr[r1].length; i++) {
                arr[r1][i]=arr[r2][i];
            }
            for (int i = 0; i < arr[r2].length; i++) {
                arr[r2][i]=temp[i];
            }
        }
    }
    public static void scaling(double arr[][],double scale , int row)
    {
        for (int i = 0; i < arr[row].length; i++) {
            arr[row][i] *= scale;
//            if(arr[row][i]>= -0.001 && arr[row][i]<=0.001)
//            {
//                arr[row][i]=0.0;
//            }
            arr[row][i] = Math.round(arr[row][i]*100.0)/100.0;
        }
    }
    public static void replacement(double arr[][], int selfRow ,int multipleRow,double multiple)
    {
        double []temp = arr[multipleRow].clone();
        for (int i = 0; i < temp.length; i++) {
            temp[i]*= multiple;
        }
        for (int i = 0; i < arr[selfRow].length; i++) {
            arr[selfRow][i]+= temp[i];
            arr[selfRow][i]=Math.round(arr[selfRow][i]*100.0)/100.0;
//            if(arr[selfRow][i]>=-0.001 && arr[selfRow][i]<=0.001)
//            {
//                arr[selfRow][i]=0.0;
//            }
        }
    }
    public static void printMatrix(double arr[][],int n)
    {
        for (int i = 0; i <n ; i++) {
            for (int j = 0; j <n+1 ; j++) {
                System.out.printf("%.2f%s",arr[i][j],"  ");
            }
            System.out.println();
        }
    }
}
