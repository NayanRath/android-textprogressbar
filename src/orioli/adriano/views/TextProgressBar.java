package orioli.adriano.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * 
 * @author Adriano Orioli
 * @version 1.0
 * 
 *  
 */ 
public class TextProgressBar extends ProgressBar {

	private String text="TEXT";
		
	private int textSize = 0;
	private float textSkew = 0.0f;
	private Paint.Align textAlign = Paint.Align.LEFT;
	private float textScaleX = -1;
	private int textFillColor = Color.BLACK;

	private int textStrokeColor = Color.WHITE;
	private float textStrokeWidth = 1.0f;
	private boolean stroked = false;
	
	private Bitmap textFillBitmap;
	private Bitmap textStrokeBitmap;
	private Bitmap maskBitmap;
	private Rect src;
	private Rect dst = new Rect(0,0,0,0);
	
	
	private boolean hasSize = false;
	
	private final Paint paint = new Paint();
	private final Xfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
	
//CONSTRUCTORS	
	
	public TextProgressBar(Context context) {
		super(context);
	}

	public TextProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(attrs);
	}

	public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(attrs);
	}
	
	private void initialize(AttributeSet attrs){
		Log.i("asd", attrs.getAttributeFloatValue("custom", "textSkew", 0f)+" ");
	}
	
//SET & GET
	
	public void setText(String text) {
		this.text = text;
		if(this.hasSize) setTextBitmap();
	}
	
	public String getText() {
		return text;
	}
	
	public Bitmap getTextBitmap() {
		return textFillBitmap;
	}

	public void setTextBitmap() {
		textFillBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
		textStrokeBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
		
		Canvas textFillCanvas = new Canvas(textFillBitmap);
		Canvas textStrokeCanvas = new Canvas(textStrokeBitmap);
		
		//Draw textFillBitmap
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		
		if(this.getTextSize() == 0)
			paint.setTextSize(textFillCanvas.getHeight());
		else 
			paint.setTextSize(this.getTextSize());
				
		paint.setTextAlign(this.textAlign);
		
		paint.setFakeBoldText(true);
		
		paint.setColor(this.getFillColor());
		
		paint.setTextSkewX(this.getTextSkew());

		if((textScaleX > -1 - 0.0001f) && (textScaleX < -1 + 0.0001f))
			paint.setTextScaleX(textFillCanvas.getWidth()/paint.measureText(text));	
		else 
			paint.setTextScaleX(this.getTextScaleX());	
		
		textFillCanvas.drawText(this.getText(),-5,textFillCanvas.getHeight()-5,paint);
		paint.reset();

		//Draw textStrokeBitmap
		paint.setAntiAlias(true);
		if(this.getTextSize() == 0)
			paint.setTextSize(textStrokeCanvas.getHeight());
		else 
			paint.setTextSize(this.getTextSize());
		
		paint.setTextAlign(this.textAlign);
		
		paint.setFakeBoldText(true);
		
		paint.setColor(this.getStrokeColor());
		
		paint.setTextSkewX(this.getTextSkew());

		if((textScaleX > -1 - 0.0001f) && (textScaleX < -1 + 0.0001f))
			paint.setTextScaleX(textStrokeCanvas.getWidth()/paint.measureText(text));	
		else 
			paint.setTextScaleX(this.getTextScaleX());	
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(this.getTextStrokeWidth());
		
		textStrokeCanvas.drawText(this.text,-5,textStrokeCanvas.getHeight()-5,paint);
		paint.reset();
	}
	
	public float getTextSkew() {
		return textSkew;
	}
	
	/**
	 * Only skews text, the text may go out-of-bounds if you don't change
	 * the default size
	 * @param skew
	 */
	public void setTextSkew(float skew) {
		this.textSkew = skew;
	}

	public int getTextSize() {
		return textSize;
	}
	
	/**
	 * Set this to 0 for text that fills vertically, 
	 * anything greater then 0 otherwise
	 * @param textSize defaults to 0
	 */
	public void setTextSize(int textSize) {
		if(textSize >= 0)
			this.textSize = textSize;
	}

	public float getTextScaleX() {
		return textScaleX;
	}

	/**
	 * Set this to -1 for text that stretches to fit the view,
	 * or anything greater then 0 otherwise
	 * @param textScaleX defaults to -1
	 */
	public void setTextScaleX(float textScaleX) {
		if( textScaleX > 0 || ((textScaleX > -1 - 0.0001f) && (textScaleX < -1 + 0.0001f)) )
			this.textScaleX = textScaleX;
	}

	public Paint.Align getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(Paint.Align textAlign) {
		this.textAlign = textAlign;
	}

	public int getFillColor() {
		return textFillColor;
	}

	public void setFillColor(int color) {
		this.textFillColor = color;
	}
	
	public int getStrokeColor() {
		return textStrokeColor;
	}

	public boolean isStroked() {
		return stroked;
	}

	public void setStroked(boolean stroked) {
		this.stroked = stroked;
	}

	public void setStrokeColor(int strokeColor) {
		this.textStrokeColor = strokeColor;
	}

	public float getTextStrokeWidth() {
		return textStrokeWidth;
	}

	public void setTextStrokeWidth(float textStrokeWidth) {
		this.textStrokeWidth = textStrokeWidth;
	}

	private void setMaskBitmap(){
		maskBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas maskCanvas = new Canvas(maskBitmap);
		maskCanvas.drawRect(0, 0, 1, maskCanvas.getHeight(), paint);
		src = new Rect(0,0,1,maskCanvas.getHeight());
	}
		
//OVERRIDES	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(w != 0 && h != 0){
			this.hasSize = true;
			this.setTextBitmap();
			setMaskBitmap();
		}
	}
		
	@Override
	protected synchronized void onDraw(Canvas canvas) {	
				
		float ratio = (float)((getProgress())/(float)(getMax()));
		int width = (int) (canvas.getWidth() * ratio);
		
		dst.set(0, 0, width, canvas.getHeight());
		
		if(this.isStroked())
			canvas.drawBitmap(textStrokeBitmap, 0, 0, paint);
		
		canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), paint, Canvas.ALL_SAVE_FLAG);
		
		//DST
		canvas.drawBitmap(maskBitmap, src, dst, paint);
		
		paint.setXfermode(mode);
		
		//SRC
		canvas.drawBitmap(this.textFillBitmap,0 , 0, paint);
		
		paint.reset();	
	}
}
