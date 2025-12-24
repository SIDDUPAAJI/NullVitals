package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean upPressed,downPressed,leftPressed,rightPressed,enterPressed,shootKeyPressed;
boolean showDebugText=false;

public KeyHandler(GamePanel gp) {
	this.gp=gp;
}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void titleState(int code) {
		if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum=2;
			}
		}
        if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum>2) {
				gp.ui.commandNum=0;
			}
		}
        if(code==KeyEvent.VK_ENTER) {
        	if(gp.ui.commandNum == 0) {
        	    gp.restart();
        	    gp.gameState = gp.playState;
        	    gp.playMusic(0);
        	}

        	if(gp.ui.commandNum==1) {
        		gp.saveLoad.load();
        		gp.gameState=gp.playState;
        		System.out.println("After load - player level: " + gp.player.level);
        		System.out.println("After load - player position: " + gp.player.worldX + ", " + gp.player.worldY);
        		System.out.println("After load - inventory size: " + gp.player.inventory.size());

        		gp.playMusic(4);
        	}
        	if(gp.ui.commandNum==2) {
        		System.exit(0);
        	}
        }
	}
	public void playState(int code) {
		if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP) {
			upPressed=true;
		}
        if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
			downPressed=true;
		}
        if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT) {
	        leftPressed=true;
        }
        if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT) {
	        rightPressed=true;
        }
        if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP) {
			upPressed=true;
		}
        if(code==KeyEvent.VK_P) {
        	gp.gameState=gp.pauseState;
	        
        }	
        if(code==KeyEvent.VK_ENTER) {
        	enterPressed=true;
	        
        }	
        if(code==KeyEvent.VK_Q) {
        	gp.gameState=gp.characterState;
        }
        if(code==KeyEvent.VK_F) {
        	shootKeyPressed=true;
	        
        }
        if(code==KeyEvent.VK_ESCAPE) {
        	gp.gameState=gp.optionState;
        }
        if(code == KeyEvent.VK_F5) {
           gp.saveLoad.save();
           gp.ui.showMessage("Game Saved");
          
         }
	
	
	//DEBUG
        if(code==KeyEvent.VK_R) {
        	switch(gp.currentmap) {
        	case 0:gp.tileM.loadMap("/maps/hospitalmap.txt",0);break;
        	case 1:gp.tileM.loadMap("/maps/secretroom.txt",1);break;
        	case 2:gp.tileM.loadMap("/maps/miniboss_map.txt",2);break;
        	case 3:gp.tileM.loadMap("/maps/bossroom.txt", 3);break;
        	}
        	}
        	}
        	
       
	
	public void pauseState(int code) {
		if(code==KeyEvent.VK_P) {
        	gp.gameState=gp.playState; 
        	gp.stopMusic(code);
        }	
	}
    public void dialogueState(int code) {
    	if(code == KeyEvent.VK_ENTER) {
			gp.gameState=gp.playState;
		}
    }
    public void characterState(int code) {
    	if(code==KeyEvent.VK_Q) {
			gp.gameState=gp.playState;
		}
    	
        if(code==KeyEvent.VK_ENTER) {
        	gp.player.selectItem();
}
        playerInventory(code);
    }
	@Override
	public void keyPressed(KeyEvent e) {
	    int code = e.getKeyCode();

	    // Handle endingState first to give it priority
	    if (gp.gameState == gp.endingState) {
	        if (code == KeyEvent.VK_ENTER) {
	            gp.ui.advanceEndingDialogue();
	        }
	        return;  // Prevent other input processing during ending
	    }

	    // Title State
	    if (gp.gameState == gp.titleState) {
	        titleState(code);
	    }
	    // Play State
	    else if (gp.gameState == gp.playState) {
	        playState(code);
	    }
	    // Pause State
	    else if (gp.gameState == gp.pauseState) {
	        pauseState(code);
	    }
	    // Dialogue State
	    else if (gp.gameState == gp.dialogueState) {
	        dialogueState(code);
	    }
	    // Character State
	    else if (gp.gameState == gp.characterState) {
	        characterState(code);
	    }
	    // Options State
	    else if (gp.gameState == gp.optionState) {
	        optionsState(code);
	    }
	    // Game Over State
	    else if (gp.gameState == gp.gameOverState) {
	        gameOverState(code);
	    }
	    // Trade State
	    else if (gp.gameState == gp.tradeState) {
	        tradeState(code);
	    }
	}

	public void  optionsState(int code) {
		if(code==KeyEvent.VK_ESCAPE) {
			gp.gameState=gp.playState;
		}
		if(code==KeyEvent.VK_ENTER) {
			enterPressed=true;
		}
		int maxCommandNum=0;
		switch(gp.ui.subState) {
		case 0:maxCommandNum=4;
		}
		if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			gp.playSE(6);
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum=maxCommandNum;
			}
		}
		if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			gp.playSE(6);
			if(gp.ui.commandNum>maxCommandNum) {
				gp.ui.commandNum=0;
			}
		}
		if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT) {
			if(gp.ui.subState==0) {
				if(gp.ui.commandNum==0 && gp.music.volumeScale>0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(6);
				}
				if(gp.ui.commandNum==1 && gp.music.volumeScale>0) {
					gp.se.volumeScale--;
					gp.se.checkVolume();
					gp.playSE(6);
				}
			}
		}
        if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT) {
        	if(gp.ui.subState==0) {
				if(gp.ui.commandNum==0 && gp.music.volumeScale<5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(6);
				}
				if(gp.ui.commandNum==1 && gp.music.volumeScale<5) {
					gp.se.volumeScale++;
					gp.se.checkVolume();
					gp.playSE(6);
				}
			}
		}
		
	}
	public void gameOverState(int code){
		if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP) {
			gp.ui.commandNum--;
			if(gp.ui.commandNum<0) {
				gp.ui.commandNum=1;
			}
			gp.playSE(6);
		}
		if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
			gp.ui.commandNum++;
			if(gp.ui.commandNum>1) {
				gp.ui.commandNum=0;
			}
			gp.playSE(6);
		}
		if(code==KeyEvent.VK_ENTER) {
			if(gp.ui.commandNum==0) {
				gp.gameState=gp.playState;
				gp.retry();
			}
			else if(gp.ui.commandNum==1) {
				gp.gameState=gp.titleState;
				gp.restart();
			}
		}
	}
	public void tradeState(int code) {
		if(code==KeyEvent.VK_ENTER) {
			enterPressed=true;
		}
		if(gp.ui.subState==0) {
			if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum<0) {
					gp.ui.commandNum=2;
				}
				gp.playSE(6);
			}if(gp.ui.subState==0) {
				if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
					gp.ui.commandNum++;
					if(gp.ui.commandNum>2) {
						gp.ui.commandNum=0;
					}
					gp.playSE(6);
				}
			
			}
		}
		if(gp.ui.subState==1) {
			npcInventory(code);
			if(code==KeyEvent.VK_ESCAPE) {
				gp.ui.subState=0;
			}
		}
		if(gp.ui.subState==2) {
			playerInventory(code);
			if(code==KeyEvent.VK_ESCAPE) {
				gp.ui.subState=0;
			}
		}
	}
	public void playerInventory(int code) {
		if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP )  {
    		if(gp.ui.playerslotRow!=0) {
    		gp.ui.playerslotRow--;}
    		
    	}
        if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT) {
        	if(gp.ui.playerslotCol!=0) {
    		gp.ui.playerslotCol--;
        	}
    		
    	}
        if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
        	if(gp.ui.playerslotRow!=3) {
    		gp.ui.playerslotRow++;
        	}
    		
    	}
        if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT) {
        	if(gp.ui.playerslotCol!=4) {
        	gp.ui.playerslotCol++;
        	}   	
}
	}
	public void npcInventory(int code) {
		if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP )  {
    		if(gp.ui.npcslotRow!=0) {
    		gp.ui.npcslotRow--;}
    		
    	}
        if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT) {
        	if(gp.ui.npcslotCol!=0) {
    		gp.ui.npcslotCol--;
        	}
    		
    	}
        if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
        	if(gp.ui.npcslotRow!=3) {
    		gp.ui.npcslotRow++;
        	}
    		
    	}
        if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT) {
        	if(gp.ui.npcslotCol!=4) {
        	gp.ui.npcslotCol++;
        	}   	
}
	}
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode()	;
		if(code==KeyEvent.VK_W || code==KeyEvent.VK_UP) {
			upPressed=false;
		}
        if(code==KeyEvent.VK_S || code==KeyEvent.VK_DOWN) {
			downPressed=false;
		}
        if(code==KeyEvent.VK_A || code==KeyEvent.VK_LEFT) {
	        leftPressed=false;
        }
        if(code==KeyEvent.VK_D || code==KeyEvent.VK_RIGHT) {
	        rightPressed=false;
        }
        if(code==KeyEvent.VK_F) {
        	shootKeyPressed=false;
	        
        }	
		
	}

	
	
	
	
	
}
