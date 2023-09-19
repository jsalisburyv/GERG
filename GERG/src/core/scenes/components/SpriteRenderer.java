package core.scenes.components;

import core.scenes.Component;

public class SpriteRenderer extends Component {

    private boolean first = true;
    @Override
    public void start() {
        //System.out.println("starting");
    }

    @Override
    public void update(double deltaTime) {
        if(first){
            //System.out.println("updating");
            first = false;
        }
    }
}
