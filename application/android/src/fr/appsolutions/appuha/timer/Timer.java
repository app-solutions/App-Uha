package fr.appsolutions.appuha.timer;

import static fr.appsolutions.appuha.utilitaires.Utilitaires.TIMER;

public class Timer {

	private Thread thread;
	
	private long time;

	public Timer(long time){
		this.time = time;
	}


	public void doTime(){

		thread = new Thread(){
			@Override
			public void run(){
				try {
					TIMER = false;
					Thread.sleep(time);
					TIMER = true;
				} catch (InterruptedException e) {
					
				}
			}	
		};
		thread.start();
	}

}
