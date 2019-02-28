# 安卓常用项目大合集
### 这里记录平时安卓开发时常用到的一些功能点，三方控件，方便以后查用
## 一，FlexboxLayoutManager+ RecyclerView实现流式布局。折叠导航栏，导航栏和列表联动
![](https://upload-images.jianshu.io/upload_images/6273713-a1daef1d3077d24e.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/400)
## 二，雷达图（蜘蛛网图）
- 1，蜘蛛网放大动效
- 2，分类标题点击变颜色
- 3，正五边形
![](https://github.com/qiushi123/demo3/blob/master/images/%E9%9B%B7%E8%BE%BE.png?raw=true)

## 三，海报分享，截取长图
原理：就是把view布局转化为bitmap，然后把bitmap分享出去
![海报分享，长图截取分享](https://github.com/qiushi123/demo3/blob/master/images/haibao.png?raw=true)
### 核心代码如下
```
private void createPoster() {
        int width = rootView.getMeasuredWidth();
        int height = rootView.getMeasuredHeight();
        /*
         * Config.RGB_565:每个像素2字节（byte）
         * ARGB_4444：2字节（已过时）
         * ARGB_8888:4字节
         * RGBA_F16：8字节
         * */
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        rootView.draw(canvas);

        //上面生成的bitmap就是我们所需要的海报
        imageView.setImageBitmap(bitmap);
        Log.i("qcl0227", "宽*高=" + bitmap.getWidth() * bitmap.getHeight());//宽*高=486720
        Log.i("qcl0227", "bitmap大小=" + bitmap.getByteCount());//bitmap大小=973440
    }
  ```
