package Project.GUI.Entities.Player.PlayerStates;

import Project.GUI.Entities.Player.Player;
import Project.GUI.Entities.Player.Skater;
import Project.GUI.Entities.Player.SkaterStats;
import Project.GUI.Entities.Puck;

import java.awt.geom.Point2D;

import static java.lang.Math.sqrt;

public class DefaultPlayerState implements PlayerState {
    private Player player;
    private SkaterStats playerStats;

    public DefaultPlayerState(Player player) {
        this.player = player;
        this.playerStats = (SkaterStats) player.getStats();

    }

    /**
     * Calculates the point of interception for one object starting at point
     * <code>a</code> with speed vector <code>v</code> and another object
     * starting at point <code>b</code> with a speed of <code>s</code>.
     *
     * @see <a
     *      href="http://jaran.de/goodbits/2011/07/17/calculating-an-intercept-course-to-a-target-with-constant-direction-and-velocity-in-a-2-dimensional-plane/">Calculating
     *      an intercept course to a target with constant direction and velocity
     *      (in a 2-dimensional plane)</a>
     *
     * @param a
     *            start vector of the object to be intercepted
     * @param v
     *            speed vector of the object to be intercepted
     * @param b
     *            start vector of the intercepting object
     * @param s
     *            speed of the intercepting object
     * @return vector of interception or <code>null</code> if object cannot be
     *         intercepted or calculation fails
     *
     * @author Jens Seiler
     */
    public static Point2D calculateInterceptionPoint(final Point2D a, final Point2D v, final Point2D b, final double s) {
        final double ox = a.getX() - b.getX();
        final double oy = a.getY() - b.getY();
        System.out.println(ox + " " + oy);

        final double h1 = v.getX() * v.getX() + v.getY() * v.getY() - s * s;
        final double h2 = ox * v.getX() + oy * v.getY();
        System.out.println(h1 + " " + h2);
        double t;
        if (h1 == 0) { // problem collapses into a simple linear equation
            t = -(ox * ox + oy * oy) / (2*h2);
        } else { // solve the quadratic equation
            final double minusPHalf = -h2 / h1;

            double discriminant = minusPHalf * minusPHalf - (ox * ox + oy * oy) / h1; // term in brackets is h3
            if (discriminant < 0) { // no (real) solution then...
                System.out.println("Oops2");
                discriminant = 1;
                //return null;
            }

            final double root = Math.sqrt(discriminant);

            final double t1 = minusPHalf + root;
            final double t2 = minusPHalf - root;

            final double tMin = Math.min(t1, t2);
            final double tMax = Math.max(t1, t2);

            t = tMin > 0 ? tMin : tMax; // get the smaller of the two times, unless it's negative
            if (t < 0) { // we don't want a solution in the past
                t=1;
                System.out.println("Oops");
                //return null;
            }
        }

        // calculate the point of interception using the found intercept time and return it
        return new Point2D.Double(a.getX() + t * v.getX(), a.getY() + t * v.getY());
    }

    private float distanceToPuck(){
        float puckX = player.getGame().getPuck().getX();
        float puckY = player.getGame().getPuck().getY();

        float deltaX = puckX - player.getX();
        float deltaY = puckY - player.getY();
        float distanceToPuck = (float) sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        return distanceToPuck;
    }

    private void moveTowardsPuck(){
        float puckX = player.getGame().getPuck().getX();
        float puckY = player.getGame().getPuck().getY();
        float distanceToPuck = distanceToPuck();

        float puckVelocityX = -(player.getGame().getPuck().getVelocityX());
        float puckVelocityY = -(player.getGame().getPuck().getVelocityY());
        int ticksToPuck = (int) (distanceToPuck/Math.min(puckVelocityX,puckVelocityY));
        float predictedX = puckX+(puckVelocityX*ticksToPuck);
        float predictedY = puckY+(puckVelocityY*ticksToPuck);
//        Point2D puckPoint = new Point2D.Float(player.getGame().getPuck().getX(),player.getGame().getPuck().getY());
//        Point2D puckVelocity = new Point2D.Float(player.getGame().getPuck().getVelocityX(),player.getGame().getPuck().getVelocityY());
//        Point2D playerPoint = new Point2D.Float(player.getX(),player.getY());
        double playerSpeed = ((Skater)player).getSpeed();
//        Point2D interceptPoint = calculateInterceptionPoint(puckPoint,puckVelocity,playerPoint,playerSpeed);
        //player.setTargetPositionAbsolute(predictedX, predictedY);
        System.out.println(puckVelocityX);
        System.out.println(puckVelocityY);
        player.setTargetPositionAbsolute(puckX,puckY);

//        float moveAngle = (float) Math.atan2(deltaY,deltaX);
//
//        if(interceptPoint != null){
//            player.setTargetPositionAbsolute((float) interceptPoint.getX(), (float) interceptPoint.getY());
//        }
//        else {
//            System.out.println("NULL");
//            ((Skater) player).move(moveAngle);
//        }
    }

//    private void contactWithPuck(){
//        if(player.playerPuckCollision(player)){
//            //collision with puck. Does player collect puck
//            //puck handling, skating, randomness, puck speed
//            int puckCollectionChance = 0;
//            puckCollectionChance += playerStats.getStatPuckHandling()*0.5; //50% PH
//            puckCollectionChance += playerStats.getStatSkating() *0.25; //20% SK
//            int puckSpeed = player.getGame().getPuck().getSpeed();
//            //check if collect puck
//            if (puckCollectionChance > player.getGame().random(100+puckSpeed*5)){
//                //collects puck
//                ((Skater)player).setHasPuck();
//            }
//        }
//    }

    private void pickUpPuck(){
        if (distanceToPuck()<25){
            //pick up puck
            ((Skater)player).setHasPuck();
        }
    }


    @Override
    public void hit(float xOffset, float yOffset, float xMove, float yMove) {


    }

    @Override
    public void pass() {

    }

    @Override
    public void think() {
        if (((Skater)player).getFaceoffTimer()>10) {
            if (((Skater)player).getFaceoffTimer()>30) {
                moveTowardsPuck();
            }
            pickUpPuck();
        }

    }

    @Override
    public void act() {

    }
}
