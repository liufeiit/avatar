package com.itjiehun.nova.cube;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Cube {

	private IntBuffer vertexBuffer;
	private IntBuffer colorBuffer;
	private ByteBuffer indexBuffer;
	public Cube() {
		super();
		int one = 65536;
		int vertex[] = {
				-one, -one, -one,
				one, -one, -one,
				one, one, -one,
				-one, one, -one,
				-one, -one, one,
				one, -one, one,
				one, one, one,
				-one, one, one
		};
		int colors[] = {
				0, 0, 0, one,
				one, 0, 0, one,
				one, one, 0, one,
				0, one, 0, one,
				0, 0, one, one,
				one, 0, one, one,
				one, one, one, one,
				0, one, one, one
		};
		byte index[] = {
				0,4,5,0,5,1,
				1,5,6,1,6,2,
				2,6,7,2,7,3,
				3,7,4,3,4,0,
				4,7,6,4,6,5,
				3,0,1,3,1,2
		};
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertex.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asIntBuffer();
		vertexBuffer.put(vertex);
		vertexBuffer.position(0);
		
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asIntBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
		
		indexBuffer = ByteBuffer.allocateDirect(index.length);
		indexBuffer.put(index);
		indexBuffer.position(0);
	}
	
	public void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CW);
		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
		gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, indexBuffer);
	}
}