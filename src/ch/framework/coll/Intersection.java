package ch.framework.coll;

public class Intersection extends Point {

    private float t;

    public Intersection(float x, float y, float t) {
        super(x, y);
        this.t = t;
    }

    public static Intersection getIntersection(Vector ray, Vector vector) {

        //RAY
        float r_px = ray.getA().getX();
        float r_py = ray.getA().getY();
        float r_dx = ray.getB().getX() - ray.getA().getX();
        float r_dy = ray.getB().getY() - ray.getA().getY();

        //SEGMENT
        float s_px = vector.getA().getX();
        float s_py = vector.getA().getY();
        float s_dx = vector.getB().getX() - vector.getA().getX();
        float s_dy = vector.getB().getY() - vector.getA().getY();

        //If Parallel
        float r_mag = (float) Math.sqrt(r_dx*r_dx+r_dy*r_dy);
        float s_mag = (float) Math.sqrt(s_dx*s_dx+s_dy*s_dy);
        if(r_dx/r_mag==s_dx/s_mag && r_dy/r_mag==s_dy/s_mag) { // Directions are the same.
            return null;
        }

        //Solve for T1 & T2
        float t2 = (r_dx*(s_py-r_py) + r_dy*(r_px-s_px))/(s_dx*r_dy - s_dy*r_dx);
        float t1 = (s_px+s_dx*t2-r_px)/r_dx;

        //Some magic
        if(t1<0) return null;
        if(t2<0 || t2>1) return null;

        return new Intersection(r_px+r_dx*t1, r_py+r_dy*t1, t1);
    }

    public static void main(String[] args) {
        Vector v1 = new Vector(-10, 0, 10, 0);
        Vector v2 = new Vector(0, 10, 4, -9);

        Intersection i = Intersection.getIntersection(v1, v2);
        System.out.println(i.t);
    }
}
