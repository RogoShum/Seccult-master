package testmod.seccult.util.MathHelper;

import java.util.Formatter;
import java.util.Locale;

public class Quat
{
    public double x;
    public double y;
    public double z;
    public double s;
    
    public Quat() {
        this.s = 1.0;
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }
    
    public Quat( Quat quat) {
        this.x = quat.x;
        this.y = quat.y;
        this.z = quat.z;
        this.s = quat.s;
    }
    
    public Quat( double d,  double d1,  double d2,  double d3) {
        this.x = d1;
        this.y = d2;
        this.z = d3;
        this.s = d;
    }
    
    public void set( Quat quat) {
        this.x = quat.x;
        this.y = quat.y;
        this.z = quat.z;
        this.s = quat.s;
    }
    
    public static Quat aroundAxis( double ax,  double ay,  double az, double angle) {
        angle *= 0.5;
         double d4 = Math.sin(angle);
        return new Quat(Math.cos(angle), ax * d4, ay * d4, az * d4);
    }
    
    public void multiply( Quat quat) {
         double d = this.s * quat.s - this.x * quat.x - this.y * quat.y - this.z * quat.z;
         double d2 = this.s * quat.x + this.x * quat.s - this.y * quat.z + this.z * quat.y;
         double d3 = this.s * quat.y + this.x * quat.z + this.y * quat.s - this.z * quat.x;
         double d4 = this.s * quat.z - this.x * quat.y + this.y * quat.x + this.z * quat.s;
        this.s = d;
        this.x = d2;
        this.y = d3;
        this.z = d4;
    }
    
    public void rightMultiply( Quat quat) {
         double d = this.s * quat.s - this.x * quat.x - this.y * quat.y - this.z * quat.z;
         double d2 = this.s * quat.x + this.x * quat.s + this.y * quat.z - this.z * quat.y;
         double d3 = this.s * quat.y - this.x * quat.z + this.y * quat.s + this.z * quat.x;
         double d4 = this.s * quat.z + this.x * quat.y - this.y * quat.x + this.z * quat.s;
        this.s = d;
        this.x = d2;
        this.y = d3;
        this.z = d4;
    }
    
    public double mag() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z + this.s * this.s);
    }
    
    public void normalize() {
        double d = this.mag();
        if (d == 0.0) {
            return;
        }
        d = 1.0 / d;
        this.x *= d;
        this.y *= d;
        this.z *= d;
        this.s *= d;
    }
    
    public void rotate( Vector3 vec) {
         double d = -this.x * vec.x - this.y * vec.y - this.z * vec.z;
         double d2 = this.s * vec.x + this.y * vec.z - this.z * vec.y;
         double d3 = this.s * vec.y - this.x * vec.z + this.z * vec.x;
         double d4 = this.s * vec.z + this.x * vec.y - this.y * vec.x;
        vec.x = d2 * this.s - d * this.x - d3 * this.z + d4 * this.y;
        vec.y = d3 * this.s - d * this.y + d2 * this.z - d4 * this.x;
        vec.z = d4 * this.s - d * this.z - d2 * this.y + d3 * this.x;
    }
    
    @Override
    public String toString() {
         StringBuilder stringbuilder = new StringBuilder();
         Formatter formatter = new Formatter(stringbuilder, Locale.US);
        formatter.format("Quaternion:\n", new Object[0]);
        formatter.format("  < %f %f %f %f >\n", this.s, this.x, this.y, this.z);
        formatter.close();
        return stringbuilder.toString();
    }
    
    public static Quat aroundAxis( Vector3 axis,  double angle) {
        return aroundAxis(axis.x, axis.y, axis.z, angle);
    }
}
