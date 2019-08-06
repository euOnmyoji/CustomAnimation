package com.github.euonmyoji.customanimation.util;

import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;

/**
 * @author yinyangshi
 */
public class Util {
    public static final double UNIT_RAD = Math.PI / 180;
    private static final double UNIT_ANGLE = 180 / Math.PI;

    public static Vector3d get(Vector3d start, Vector3d end, double m) {
        if (m == 1) {
            return end;
        }
        return start.add(end.sub(start).mul(m));
    }

    public static Vector2d get(Vector2d start, Vector2d end, double m) {
        if (m == 1) {
            return end;
        }
        return start.add(end.sub(start).mul(m));
    }

    /**
     * get the (pitch, yaw) in the minecraft unit
     * ^x (yaw = -90)
     * |
     * |  .(z, x)
     * |
     * O------->z   (yaw = 0)
     * |
     * |
     * |
     * V-x (yaw = 90)
     *
     * @param start    the (pitch, yaw) player begin value or null & m == 1 to look point
     * @param location the location you are staying
     * @param point    the point you want to look
     * @param m        the state
     * @param offset   the angel offset for yaw
     * @return (pitch, yaw)
     */
    public static Vector2d get(Vector2d start, Vector3d location, Vector3d point, double m, double offset) {
        Vector3d seeV = point.sub(location);
        double yaw = 0;
        if (seeV.getX() == 0) {
            if (seeV.getZ() == 0) {
                if (start == null) {
                    return null;
                } else {
                    yaw = start.getX();
                }
            } else if (seeV.getZ() < 0) {
                yaw = 180;
            }
        } else if (seeV.getZ() == 0) {
            yaw = seeV.getX() > 0 ? -90 : 90;
        } else {
            double xzL = seeV.toVector2(true).length();
            yaw = -Math.asin(seeV.getX() / xzL) * UNIT_ANGLE;
            if (seeV.getZ() < 0) {
                yaw += yaw > 0 ? 90 : -90;
            }
        }
        double pitch = seeV.getY() == 0 ? 0 : -Math.asin(seeV.getY() / seeV.length());
        Vector2d result = new Vector2d(pitch, yaw);
        if (start == null || m == 1) {
            return result;
        } else {
            Vector2d d = result.sub(start);
            return start.add(d.mul(m));
        }
    }
}