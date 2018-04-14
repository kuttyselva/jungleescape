package com.kuttyselva.minionkutty;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import org.w3c.dom.css.Rect;

import java.util.Random;

public class minionkutty extends ApplicationAdapter {
	SpriteBatch batch;
	Texture back;
	Texture govr;
	ShapeRenderer shp;
	Texture [] minions;
	int fstate= 0;
	int flag=0;
	float my=0;
	float vel=0;
	int gset=0;
	float gravity=2;

	BitmapFont font;
	Texture top;
	Texture bot;
float gap=400;
	float maxoff;
	Random gen;
Circle bc;
	float tvel =4;

	int not=4;
	float[] tuboff=new float[not];
	float[] tx=new float[not];
	float dist;
	Rectangle[] tr;
	Rectangle[] br;
	int score=0;
	int st=0;


	@Override
	public void create () {
		batch = new SpriteBatch();
back=new Texture("bg.png");
govr=new Texture("go.png");
font=new BitmapFont();
font.setColor(Color.WHITE);
font.getData().setScale(10);

		//shp=new ShapeRenderer();
		bc=new Circle();
minions=new Texture[2];
minions[0]=new Texture("ml.png");
minions[1]=new Texture("mh.png");

bot=new Texture("up.png");
		top=new Texture("down.png");
		maxoff=Gdx.graphics.getHeight() /2 -gap /2 -100;
		gen=new Random();
		dist=Gdx.graphics.getWidth()*3 /4;
		tr=new Rectangle[not];
		br=new Rectangle[not];
startgame();
	}
public void startgame()
{
	my=Gdx.graphics.getHeight() / 2-minions[0].getHeight() / 2;
	for(int i=0;i<not;i++)
	{
		tuboff[i]= (float) (gen.nextFloat() -0.5) * (Gdx.graphics.getHeight() - gap -200);
		tx[i]=Gdx.graphics.getWidth() /2 -top.getWidth()/2 + Gdx.graphics.getWidth()+ i*dist;
		tr[i]=new Rectangle();
		br[i]=new Rectangle();

	}
}
	@Override
	public void render () {

		batch.begin();
		batch.draw(back, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gset == 1) {
			if(tx[st] < Gdx.graphics.getWidth()/2){
				score++;
				Gdx.app.log("score", String.valueOf(score));
				if(st <not-1){
					st++;
				}else {
					st=0;
				}
			}

			if (Gdx.input.justTouched()) {

				vel=-20;
				fstate = 0;

			}
			else {
				fstate = 1;
			}
			for(int i=0;i<not;i++) {
				if(tx[i]< - top.getWidth())
				{
					tx[i]+=not*dist;
					tuboff[i]= (float) (gen.nextFloat() -0.5) * (Gdx.graphics.getHeight() - gap -200);

				}
				else {
					tx[i] = tx[i] - tvel;

				}

				batch.draw(top, tx[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tuboff[i]);
				batch.draw(bot, tx[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bot.getHeight() + tuboff[i]);

				tr[i]=new Rectangle(tx[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tuboff[i],top.getWidth(),top.getHeight());
				br[i]=new Rectangle(tx[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bot.getHeight() + tuboff[i],bot.getWidth(),bot.getHeight());

			}
			if(my >0 )
			{
				vel=vel+gravity;
				my -= vel;
			}else {
				gset=2;
			}


		} else if (gset==0) {


			if (Gdx.input.justTouched()) {

				gset = 1;
				fstate = 1;

			}
		}else if(gset==2){
				batch.draw(govr,Gdx.graphics.getWidth()/2-govr.getWidth()/2,Gdx.graphics.getHeight()/2-govr.getHeight()/2);
				fstate = 0;
			if (Gdx.input.justTouched()) {

				gset = 1;
				fstate = 1;
				startgame();
				score=0;
				st=0;
				vel=0;


			}
			}




		batch.draw(minions[fstate], Gdx.graphics.getWidth() / 2 - minions[fstate].getWidth() / 2, my);
		font.draw(batch,String.valueOf(score),100,200);


		bc.set(Gdx.graphics.getWidth()/2,my +minions[fstate].getHeight() /2 ,minions[fstate].getWidth() /2);


		//shp.begin(ShapeRenderer.ShapeType.Filled);
		//shp.setColor(Color.RED);

		//shp.circle(bc.x,bc.y,bc.radius);
		for(int i=0;i<not;i++){
			//shp.rect(tx[i],Gdx.graphics.getHeight() / 2 + gap / 2 + tuboff[i],top.getWidth(),top.getHeight());
			//shp.rect(tx[i],Gdx.graphics.getHeight() / 2 - gap / 2 - bot.getHeight() + tuboff[i],bot.getWidth(),bot.getHeight());

			if(Intersector.overlaps(bc,tr[i]) || Intersector.overlaps(bc,br[i])){
				gset=2;
			}

		}
		batch.end();
		//shp.end();

	}
}
