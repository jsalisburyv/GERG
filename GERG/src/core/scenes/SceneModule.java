package core.scenes;

import core.Module;

public class SceneModule implements Module {

    private Scene currentScene;

    @Override
    public void startUp() {
        changeScene(0);
    }

    @Override
    public void update(float deltatime) {
        currentScene.update(deltatime);
    }

    @Override
    public void shutdown() {

    }

    public void changeScene(int newScene){
        switch(newScene) {
            case 0 -> {
                currentScene = new LevelEditorScene();
            }
            case 1 -> {
                currentScene = new LevelScene();
            }
        }
        currentScene.init();
        currentScene.start();
    }
}
