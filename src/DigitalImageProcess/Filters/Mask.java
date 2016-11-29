/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DigitalImageProcess.Filters;

/**
 *
 * @author Jorismar
 */
public class Mask {
    int offset;
    private int num_lines;
    private int num_columns;
    private float[] sharpen;
    private float[][] matrix;

    public Mask() {
        this.num_lines = 0;
        this.num_columns = 0;
        this.offset = 0;
    }
    
    public Mask(int offset) {
        this.num_lines = 0;
        this.num_columns = 0;
        this.offset = offset;
    }
    
    public boolean createMatrix(int l, int c) {
        if(l * c < 9)
            return false;

        this.num_lines = l;
        this.num_columns = c;
        
        this.matrix = new float[l][c];

        return true;
    }

    public void setMatrixValue(int l, int c, float value) {
        this.matrix[l][c] = value;
    }
    
    public void setSharpen(int c, int d) {
        this.sharpen = new float[] {-c, -c, -c, -c, (8 * c) + d, -c, -c, -c, -c};
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public int getNumLines() {
        return num_lines;
    }

    public int getNumColumns() {
        return num_columns;
    }

    public float[] getSharpen() {
        return sharpen;
    }
}
