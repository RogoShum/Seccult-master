package testmod.seccult.util.MathHelper;

import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import java.math.*;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.opengl.*;

public class Vector3
{
    public static Vector3 zero;
    public static Vector3 one;
    public static Vector3 center;
    public static Vector3 up;
    public static Vector3 down;
    public static Vector3 forward;
    public static Vector3 back;
    public static Vector3 right;
    public static Vector3 left;
    public double x;
    public double y;
    public double z;
    
    public Vector3() {
    }
    
    public Vector3(double d, double d1, double d2) {
        this.x = d;
        this.y = d1;
        this.z = d2;
    }
    
    public Vector3(Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
    
    public Vector3(Vec3d vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
    
    public Vector3 copy() {
        return new Vector3(this);
    }
    
    public static Vector3 fromEntity( Entity e) {
        return new Vector3(e.posX, e.posY, e.posZ);
    }
    
    public static Vector3 fromEntityCenter(Entity e) {
        return new Vector3(e.posX, e.posY - e.getYOffset() + e.height / 2.0f, e.posZ);
    }
    
    public static Vector3 fromTileEntity(TileEntity e) {
        return fromBlockPos(e.getPos());
    }
    
    public static Vector3 fromTileEntityCenter( TileEntity e) {
        return fromTileEntity(e).add(0.5, 0.5, 0.5);
    }
    
    public static Vector3 fromBlockPos(BlockPos pos) {
        return new Vector3(pos.getX(), pos.getY(), pos.getZ());
    }
    
    public Vector3 set( double d,  double d1,  double d2) {
        this.x = d;
        this.y = d1;
        this.z = d2;
        return this;
    }
    
    public Vector3 set( Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return this;
    }
    
    public double dotProduct(Vector3 vec) {
        double d = vec.x * this.x + vec.y * this.y + vec.z * this.z;
        if (d > 1.0 && d < 1.00001) {
            d = 1.0;
        }
        else if (d < -1.0 && d > -1.00001) {
            d = -1.0;
        }
        return d;
    }
    
    public double dotProduct(double d,  double d1,  double d2) {
        return d * this.x + d1 * this.y + d2 * this.z;
    }
    
    public Vector3 crossProduct( Vector3 vec) {
        double d = this.y * vec.z - this.z * vec.y;
        double d2 = this.z * vec.x - this.x * vec.z;
        double d3 = this.x * vec.y - this.y * vec.x;
        this.x = d;
        this.y = d2;
        this.z = d3;
        return this;
    }
    
    public Vector3 add( double d,  double d1,  double d2) {
        this.x += d;
        this.y += d1;
        this.z += d2;
        return this;
    }
    
    public Vector3 add( Vector3 vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }
    
    public Vector3 add( double d) {
        return this.add(d, d, d);
    }
    
    public Vector3 sub( Vector3 vec) {
        return this.subtract(vec);
    }
    
    public Vector3 subtract( Vector3 vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }
    
    public Vector3 negate( Vector3 vec) {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }
    
    public Vector3 multiply( double d) {
        this.x *= d;
        this.y *= d;
        this.z *= d;
        return this;
    }
    
    public Vector3 multiply( Vector3 f) {
        this.x *= f.x;
        this.y *= f.y;
        this.z *= f.z;
        return this;
    }
    
    public Vector3 multiply( double fx,  double fy,  double fz) {
        this.x *= fx;
        this.y *= fy;
        this.z *= fz;
        return this;
    }
    
    public double mag() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public double magSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public Vector3 normalize() {
        double d = this.mag();
        if (d != 0.0) {
            this.multiply(1.0 / d);
        }
        return this;
    }
    
    @Override
    public String toString() {
         MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
        return "Vector[" + new BigDecimal(this.x, cont) + ", " + new BigDecimal(this.y, cont) + ", " + new BigDecimal(this.z, cont) + "]";
    }
    
    public Vector3 perpendicular() {
        if (this.z == 0.0) {
            return this.zCrossProduct();
        }
        return this.xCrossProduct();
    }
    
    public Vector3 xCrossProduct() {
        double d = this.z;
        double d2 = -this.y;
        this.x = 0.0;
        this.y = d;
        this.z = d2;
        return this;
    }
    
    public Vector3 zCrossProduct() {
        double d = this.y;
        double d2 = -this.x;
        this.x = d;
        this.y = d2;
        this.z = 0.0;
        return this;
    }
    
    public Vector3 yCrossProduct() {
         double d = -this.z;
         double d2 = this.x;
        this.x = d;
        this.y = 0.0;
        this.z = d2;
        return this;
    }
    
    public Vec3d toVec3D() {
        return new Vec3d(this.x, this.y, this.z);
    }
    
    public BlockPos toBlockPos() {
        return new BlockPos(this.toVec3D());
    }
    
    public double angle( Vector3 vec) {
        return Math.acos(this.copy().normalize().dotProduct(vec.copy().normalize()));
    }
    
    public boolean isInside( AxisAlignedBB aabb) {
        return this.x >= aabb.minX && this.y >= aabb.maxY && this.z >= aabb.minZ && this.x < aabb.maxX && this.y < aabb.maxY && this.z < aabb.maxZ;
    }
    
    public boolean isZero() {
        return this.x == 0.0 && this.y == 0.0 && this.z == 0.0;
    }
    
    public boolean isAxial() {
        return (this.x == 0.0) ? (this.y == 0.0 || this.z == 0.0) : (this.y == 0.0 && this.z == 0.0);
    }
    
    @SideOnly(Side.CLIENT)
    public Vector3f vector3f() {
        return new Vector3f((float)this.x, (float)this.y, (float)this.z);
    }
    
    @SideOnly(Side.CLIENT)
    public Vector4f vector4f() {
        return new Vector4f((float)this.x, (float)this.y, (float)this.z, 1.0f);
    }
    
    @SideOnly(Side.CLIENT)
    public void glVertex() {
        GL11.glVertex3d(this.x, this.y, this.z);
    }
    
    public Vector3 negate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }
    
    public double scalarProject( Vector3 b) {
        double l = b.mag();
        return (l == 0.0) ? 0.0 : (this.dotProduct(b) / l);
    }
    
    public Vector3 project( Vector3 b) {
        double l = b.magSquared();
        if (l == 0.0) {
            this.set(0.0, 0.0, 0.0);
            return this;
        }
         double m = this.dotProduct(b) / l;
        this.set(b).multiply(m);
        return this;
    }
    
    public Vector3 rotate( double angle,  Vector3 axis) {
        Quat.aroundAxis(axis.copy().normalize(), angle).rotate(this);
        return this;
    }
    
    @Override
    public boolean equals( Object o) {
        if (!(o instanceof Vector3)) {
            return false;
        }
         Vector3 v = (Vector3)o;
        return this.x == v.x && this.y == v.y && this.z == v.z;
    }
    
    static {
        Vector3.zero = new Vector3();
        Vector3.one = new Vector3(1.0, 1.0, 1.0);
        Vector3.center = new Vector3(0.5, 0.5, 0.5);
        Vector3.up = new Vector3(0.0, 1.0, 0.0);
        Vector3.down = new Vector3(0.0, -1.0, 0.0);
        Vector3.forward = new Vector3(0.0, 0.0, 1.0);
        Vector3.back = new Vector3(0.0, 0.0, -1.0);
        Vector3.right = new Vector3(1.0, 0.0, 0.0);
        Vector3.left = new Vector3(-1.0, 0.0, 0.0);
    }
}
