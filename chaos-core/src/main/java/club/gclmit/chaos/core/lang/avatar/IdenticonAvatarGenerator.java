package club.gclmit.chaos.core.lang.avatar;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 * Identicon（Github 风格） 头像生成
 *
 * @author gclm
 */
public class IdenticonAvatarGenerator extends AvatarGenerator {

    /**
     * 头像生成
     *
     * @param text   内容
     * @param width  图片宽度
     * @param height 图片搞度
     * @param fillet 圆角  true 为圆角
     * @return java.awt.image.BufferedImage
     * @author gclm
     */
    public static BufferedImage generate(String text, int width, int height, boolean fillet) {
        int hash_width = 5, hash_height = 5;
        byte[] hash = text.getBytes();

        BufferedImage identicon = new BufferedImage(hash_width, hash_height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = identicon.getRaster();

        int[] background = new int[]{255, 255, 255, 0};
        int[] foreground = new int[]{hash[0] & 255, hash[1] & 255, hash[2] & 255, 255};

        for (int x = 0; x < hash_width; x++) {
            //Enforce horizontal symmetry
            int i = x < 3 ? x : 4 - x;
            for (int y = 0; y < hash_height; y++) {
                int[] pixelColor;
                //toggle pixels based on bit being on/off
                if ((hash[i] >> y & 1) == 1)
                    pixelColor = foreground;
                else
                    pixelColor = background;
                raster.setPixel(x, y, pixelColor);
            }
        }

        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        //Scale image to the size you want
        AffineTransform at = new AffineTransform();
        at.scale(width / hash_width, height / hash_height);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        finalImage = op.filter(identicon, finalImage);

        if (fillet) {
            finalImage = makeRoundedCorner(finalImage, 99);
        }

        return finalImage;
    }

}
