package com.opengg.test;

import com.opengg.core.Matrix4f;
import com.opengg.core.Vector2f;
import com.opengg.core.Vector3f;
import com.opengg.core.audio.AudioHandler;
import com.opengg.core.audio.AudioSource;
import com.opengg.core.components.ModelRenderComponent;
import com.opengg.core.components.PhysicsComponent;
import com.opengg.core.gui.GUI;
import com.opengg.core.io.input.KeyboardEventHandler;
import com.opengg.core.io.input.KeyboardListener;
import com.opengg.core.io.objloader.parser.OBJModel;
import com.opengg.core.io.objloader.parser.OBJParser;
import com.opengg.core.movement.MovementLoader;
import com.opengg.core.render.VertexArrayObject;
import com.opengg.core.render.VertexBufferObject;
import com.opengg.core.render.buffer.ObjectBuffers;
import com.opengg.core.render.drawn.DrawnObject;
import com.opengg.core.render.drawn.DrawnObjectGroup;
import static com.opengg.core.render.gl.GLOptions.enable;
import com.opengg.core.render.shader.Mode;
import com.opengg.core.render.shader.ShaderController;
import com.opengg.core.render.texture.Cubemap;
import com.opengg.core.render.texture.Font;
import com.opengg.core.render.texture.Texture;
import com.opengg.core.render.window.DisplayMode;
import com.opengg.core.render.window.GLFWWindow;
import static com.opengg.core.render.window.RenderUtil.endFrame;
import static com.opengg.core.render.window.RenderUtil.startFrame;
import com.opengg.core.util.GlobalInfo;
import com.opengg.core.world.Camera;
import com.opengg.core.world.Terrain;
import com.opengg.core.world.World;
import com.opengg.core.engine.WorldManager;
import com.opengg.core.io.newobjloader.Parser;
import com.opengg.core.model.OBJ;
import com.opengg.core.world.WorldObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.FloatBuffer;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL32.GL_TEXTURE_CUBE_MAP_SEAMLESS;

public class OpenGGTest implements KeyboardListener {

    static long window;
    GLFWWindow win;
    boolean draw = true;
    private float ratio;

    public float xrot, yrot;
    public float rot1 = 0, rot2 = 0;
    public float xm = 0, ym = 0, zm = 0;
    int quads;
    Vector3f rot = new Vector3f(0, 0, 0);
    Vector3f pos = new Vector3f(0, -10, -30);
    WorldObject terrain;
    WorldObject drawnobject;
    
    World w;
    
    public static void main(String[] args) throws IOException, Exception {
        new OpenGGTest();
    }

    private VertexArrayObject vao;
    private VertexBufferObject vbo;

    Camera c;
    GUI g = new GUI();
    Texture t1 = new Texture();
    Texture t2 = new Texture();
    Texture ppbf = new Texture();

    DrawnObject flashbang, test5, base2, sky;
    DrawnObjectGroup test6, awp3;

    float speed = 0.2f;

    OBJModel m;
    OBJModel m2;
    private Font f;
    private Texture t3 = new Texture();
    private Cubemap cb = new Cubemap();
    private ShaderController s = new ShaderController();
    
    WorldObject w1, w2;
    private AudioSource so,so2,so3;
    private DrawnObject ppsht;
    
    public OpenGGTest() throws IOException, Exception {
        KeyboardEventHandler.addToPool(this);

   
        try {
            win = new GLFWWindow(1280, 960, "Test", DisplayMode.WINDOWED);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setup();
        while (!win.shouldClose()) {
            startFrame();

            update(1);
            render();
            endFrame(win);
        }
        
        exit();
    }

    FloatBuffer base, test2, test;

    public void setup() throws FileNotFoundException, IOException, Exception {
        MovementLoader.setup(window, 80);

        vao = new VertexArrayObject();
        vao.bind();
        t1.setupTexToBuffer(2000,2000);
        ppbf.setupTexToBuffer(win.getWidth(), win.getHeight());
        t3.loadTexture("C:/res/deer.png", true);
        f = new Font("", "thanks dad", 11);

        t2.loadFromBuffer(f.asByteBuffer(), (int) f.getFontImageWidth(), (int) f.getFontImageHeight());
        t2.useTexture(0);
        cb.loadTexture("C:/res/skybox/majestic");

//        AudioHandler.init(1);
//        int s1 = AudioHandler.loadSound(OpenGGTest.class.getResource("res/maw.wav"));
//        so = new AudioSource(s1);
//            
//        int s12 = AudioHandler.loadSound(OpenGGTest.class.getResource("res/mgs.wav"));
//        so2 = new AudioSource(s12);
//        
//        int s13 = AudioHandler.loadSound(OpenGGTest.class.getResource("res/stal.wav"));
//        so3 = new AudioSource(s13);

        URL path = OpenGGTest.class.getResource("res/models/deer.obj");
        URL path2 = OpenGGTest.class.getResource("res/models/awp3.obj");
        m = new OBJParser().parse(path);
        m2 = new OBJParser().parse(path2);

        URL verts = OpenGGTest.class.getResource("res/shaders/shader.vert");
        URL frags = OpenGGTest.class.getResource("res/shaders/shader.frag");
        URL geoms = OpenGGTest.class.getResource("res/shaders/shader.geom");
        
        s.setup(verts, frags, geoms);
        GlobalInfo.main = s;
        
        c = new Camera(pos, rot);
        c.setPos(pos);
        c.setRot(rot);

        g.setupGUI(new Vector2f(-3, -3), new Vector2f(3, 3));

        test = ObjectBuffers.genBuffer(m, 1f, 0.2f, new Vector3f());
        test2 = ObjectBuffers.genBuffer(m2, 1f, 1f, new Vector3f());
        test6 = OBJ.getDrawableModel("C:/res/3DSMusicPark/3DSMusicPark.obj");
        flashbang = new DrawnObject(test2, 12);

        test2 = ObjectBuffers.getSquareUI(1, 3, 1, 3, -1, 1f, false);
        test5 = new DrawnObject(test2, 12);
        ppsht = new DrawnObject(ObjectBuffers.getSquareUI(-1, 1, -1, 1, .6f, 1, false),12);
        test2 = ObjectBuffers.genSkyCube();
        sky = new DrawnObject(test2, 12);
        
        w = WorldManager.getDefaultWorld();
        w.floorLev = -10;
        w.addObject(w1 = new WorldObject(awp3));
        w.addObject(w2 = new WorldObject(flashbang));

        flashbang.removeBuffer();
        ModelRenderComponent m = new ModelRenderComponent(base2);
        ModelRenderComponent l = new ModelRenderComponent(awp3);

        l.setOffset(new Vector3f(10,30,0));

        terrain = new WorldObject();
        terrain.attach(l);
        terrain.attach(m);
        PhysicsComponent bad = new PhysicsComponent(terrain);
        bad.force = new Vector3f(1,1,0);
        terrain.attach(bad);
        terrain.pos = new Vector3f(0,0,0);
        ratio = win.getRatio();
        
        enable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        enable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);

        enable(GL_TEXTURE_2D);

        enable(GL_TEXTURE_CUBE_MAP_SEAMLESS);
        
        //enable(GL_LINE_SMOOTH);
        
        glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
    }

    public void exit() {
       
        AudioHandler.destroy();
        vao.delete();
    }

    public void render() {
        rot = new Vector3f(-yrot, -xrot, 0);
        pos = MovementLoader.processMovement(pos, rot);
        
        c.setPos(new Vector3f(15, -40, -10));
        c.setRot(new Vector3f(60, 50, 0));

        s.setLightPos(new Vector3f(40, 80, 40));
        s.setView(c);
        ppbf.startTexRender();
        
        s.setPerspective(90, ratio, 4, 300f);      
        s.setMode(Mode.POS_ONLY);
        s.setMode(Mode.OBJECT);
        
        c.setPos(pos);
        c.setRot(rot);

        s.setView(c);
        s.setPerspective(90, ratio, 0.3f, 2500f);
        
        test6.drawShaded();
        t3.useTexture(0);
        flashbang.drawShaded();
        s.setMode(Mode.SKYBOX);
        cb.use(2);
        sky.draw();
        
        s.setMode(Mode.GUI);
        g.startGUI();
        ppbf.endTexRender();
        s.setMode(Mode.PP);
        s.setOrtho(-1, 1, -1, 1, -1, 1);
        s.setView(new Camera());
        ppbf.useTexture(0);
        ppbf.useDepthTexture(1);
        ppsht.draw();
    }

    public void update(float delta) {
        xrot += rot1 * 5;
        yrot += rot2 * 5;
        //terrain.update(delta);
      
    }

    @Override
    public void keyPressed(int key) {

        if (key == GLFW_KEY_Q) {
            rot1 += 0.3;

        }
        if (key == GLFW_KEY_E) {
            rot1 -= 0.3;

        }
        if (key == GLFW_KEY_R) {
            rot2 += 0.3;

        }
        if (key == GLFW_KEY_F) {
            rot2 -= 0.3;

        }
        if (key == GLFW_KEY_P) {
            if(so2.isPaused()){
                so2.play();
            }else{
                so2.pause();
            }
        }

    }

    @Override
    public void keyReleased(int key) {

        if (key == GLFW_KEY_Q) {
            rot1 -= 0.3;

        }
        if (key == GLFW_KEY_E) {
            rot1 += 0.3;

        }
        if (key == GLFW_KEY_R) {
            rot2 -= 0.3;

        }
        if (key == GLFW_KEY_F) {
            rot2 += 0.3;

        }
    }
}
