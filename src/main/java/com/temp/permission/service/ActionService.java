package com.temp.permission.service;

import com.temp.admin.controller.ConfigureController;
import com.temp.generator.controller.DatabaseController;
import com.temp.permission.consts.BackendConst;
import com.temp.permission.controller.ActionController;
import com.temp.permission.entity.Resource;
import com.temp.permission.model.dto.ResourceDTO;
import com.temp.permission.model.dto.param.ParamActionDTO;
import com.temp.permission.model.vo.UriVO;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Service
public class ActionService {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private Mapper mapperTrans;

    /**
     * 获取所有的类信息
     * @return list
     */
    public Map getClasses() {
        Map<String, String> map = new HashMap<>();
        List<Class<?>> classList = new ArrayList<>();
        classList.addAll(getAllClassByPackageName(ConfigureController.class.getPackage()));
        classList.addAll(getAllClassByPackageName(DatabaseController.class.getPackage()));
        classList.addAll(getAllClassByPackageName(ActionController.class.getPackage()));
        for (Class<?> cla : classList) {
            map.put(cla.getSimpleName(), cla.getName());
        }
        return map;
    }

    /**
     * 获取类下面的接口信息
     * @param className 类名称
     * @return list
     */
    public Map getResource(String className) throws ClassNotFoundException {
        Class<?> cls = Class.forName(className);
        return getAnnotationInterface(cls);
    }

    /**
     * 获取所有的权限信息
     * @return list
     */
    public List<ResourceDTO> getAllPrivilege() {
        List<ResourceDTO> list = new ArrayList<>();
        List<Resource> resourceList = resourceService.getListByType(BackendConst.RESOURCE_TYPE_API);
        ResourceDTO dto;
        for (Resource resource : resourceList) {
            dto = mapperTrans.map(resource, ResourceDTO.class);
            list.add(dto);
        }
        return list;
    }

    /**
     * 批量保存接口权限信息
     * @param dto o
     * @return boolean
     */
    public Boolean batchSave(ParamActionDTO dto) {
        return resourceService.batchSave(dto.getParentId(), dto.getResources());
    }

    /**
     * 删除接口资源权限
     * @param id id
     * @return boolean
     */
    public Boolean delete(Integer id) {
        return resourceService.delete(id);
    }

    private static void getAnnotationInfo(List<Class<?>> clsList){
        if (clsList != null && clsList.size() > 0) {
            for (Class<?> cls : clsList) {

            }
        }
    }

    /**
     * 根据类信息获取接口信息
     * @param cls 类全名
     * @return list
     */
    private Map getAnnotationInterface(Class<?> cls) {
        boolean exits = cls.isAnnotationPresent(RestController.class);
        if (!exits) {
            return null;
        }
        Map<String, UriVO> map = new HashMap<>();
        RequestMapping requestMappingCls = cls.getAnnotation(RequestMapping.class);
        String prefix = "";
        if (requestMappingCls != null) {
            prefix = requestMappingCls.value()[0];
        }

        //获取类中的所有的方法
        Method[] methods = cls.getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                boolean mExits = method.isAnnotationPresent(RequestMapping.class);
                if (!mExits) {
                    continue;
                }
                RequestMapping requestMapping = (RequestMapping) method.getAnnotation(RequestMapping.class);
                ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);

                UriVO uriVO = new UriVO();
                uriVO.setUrl(prefix + requestMapping.value()[0]);
                if (apiOperation != null) {
                    uriVO.setDesc(apiOperation.notes());
                }

                map.put(prefix + requestMapping.value()[0], uriVO);
            }
        }
        return map;
    }

    private static List<Class<?>> getAllClassByPackageName(Package pkg) {
        String packageName = pkg.getName();
        // 获取当前包下以及子包下所以的类
        List<Class<?>> returnClassList = getClasses(packageName);
        return returnClassList;
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param packageName 包名
     * @return list
     */
    private static List<Class<?>> getClasses(String packageName) {
        // 第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                System.out.println(url.getFile());
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName 包名
     * @param packagePath 包的路径
     * @param recursive
     * @param classes 类名
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirFiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });

        assert dirFiles != null;
        // 循环所有文件
        for (File file : dirFiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
