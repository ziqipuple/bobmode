package com.ld.myapplication;

import android.content.Context;
import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigUtil {
    private static final String CONFIG_FILE_NAME = "parameters.json";
    private static final String TARGET_APP_DIR = "com.ztgame.bob"; // 目标应用包名

    // 保存配置到目标应用的私有目录
    public static void saveConfigToTargetAppDir(parameters parameters) {
        File targetDir = new File(Environment.getExternalStorageDirectory(), 
                                  "Android/data/" + TARGET_APP_DIR + "/files");
        if (!targetDir.exists()) {
            targetDir.mkdirs(); // 确保目录存在
        }
        File targetFile = new File(targetDir, CONFIG_FILE_NAME);
        saveConfigWithInlineComments(parameters, targetFile);
    }

    // 从目标应用的私有目录加载配置
    public static parameters loadConfigFromTargetAppDir() {
        File targetFile = new File(Environment.getExternalStorageDirectory(), 
                                   "Android/data/" + TARGET_APP_DIR + "/files/" + CONFIG_FILE_NAME);
        return loadConfigFromFile(targetFile);
    }

    // 保存配置到应用的内部存储
    public static void saveConfig(Context context, parameters parameters) {
        File file = new File(context.getFilesDir(), CONFIG_FILE_NAME);
        saveConfig(parameters, file);
    }

    // 从应用的内部存储加载配置
    public static parameters loadConfig(Context context) {
        File file = new File(context.getFilesDir(), CONFIG_FILE_NAME);
        return loadConfigFromFile(file);
    }

    private static void saveConfigWithInlineComments(parameters parameters, File file) {
        try (FileWriter writer = new FileWriter(file)) {
			writer.write("{\n");

			// 吐球分身定义
            writer.write("  \"分辨率x\": " + parameters.分辨率x + ",\n");
            writer.write("  \"分辨率y\": " + parameters.分辨率y + ",\n");
            writer.write("  \"比例\": " + parameters.比例 + ",\n");
			writer.write("  \"吐球1秒次数\": " + parameters.吐球1秒次数 + ",\n");
			writer.write("  \"四分延时\": " + parameters.四分延时 + ",\n");
            writer.write("  \"四分后延时\": " + parameters.四分后延时 + ",\n");
			writer.write("  \"四分后分身间隔\": " + parameters.四分后分身间隔 + ",\n");
			writer.write("  \"冲球指定次数\": " + parameters.冲球指定次数 + ",\n");
        	writer.write("  \"冲球分身间隔\": " + parameters.冲球分身间隔 + ",\n");
			writer.write("  \"长按分身延时\": " + parameters.长按分身延时 + ",\n");
            writer.write("  \"长按分身间隔\": " + parameters.长按分身间隔 + ",\n");
            writer.write("  \"卡点过滤\": " + parameters.卡点过滤 + ",\n");
            writer.write("  \"卡点前摇\": " + parameters.卡点前摇 + ",\n");
            writer.write("  \"卡点分身次数\": " + parameters.卡点分身次数 + ",\n");
            writer.write("  \"卡点分身间隔\": " + parameters.卡点分身间隔 + ",\n");
            writer.write("  \"同步前摇\": " + parameters.同步前摇 + ",\n");
            writer.write("  \"同步滑动长度\": " + parameters.同步滑动长度 + ",\n");
            writer.write("  \"同步滑动时长\": " + parameters.同步滑动时长 + ",\n");
            writer.write("  \"同步分身次数\": " + parameters.同步分身次数 + ",\n");
            writer.write("  \"同步分身间隔\": " + parameters.同步分身间隔 + ",\n");

            // 三角定义
			writer.write("  \"三角1滑动角度\": " + parameters.三角1滑动角度 + ",\n");
			writer.write("  \"三角1滑动长度\": " + parameters.三角1滑动长度 + ",\n");
			writer.write("  \"三角1滑动时长\": " + parameters.三角1滑动时长 + ",\n");

			writer.write("  \"三角1点击延时\": " + parameters.三角1点击延时 + ",\n");
			writer.write("  \"三角2滑动延时\": " + parameters.三角2滑动延时 + ",\n");

			writer.write("  \"三角2滑动角度\": " + parameters.三角2滑动角度 + ",\n");
			writer.write("  \"三角2滑动长度\": " + parameters.三角2滑动长度 + ",\n");
			writer.write("  \"三角2滑动时长\": " + parameters.三角2滑动时长 + ",\n");

			writer.write("  \"三角2点击延时\": " + parameters.三角2点击延时 + ",\n");
			writer.write("  \"三角3滑动延时\": " + parameters.三角3滑动延时 + ",\n");

			writer.write("  \"三角3滑动角度\": " + parameters.三角3滑动角度 + ",\n");
			writer.write("  \"三角3滑动长度\": " + parameters.三角3滑动长度 + ",\n");
			writer.write("  \"三角3滑动时长\": " + parameters.三角3滑动时长 + ",\n");

			writer.write("  \"三角分身次数\": " + parameters.三角分身次数 + ",\n");
			writer.write("  \"三角分身延时\": " + parameters.三角分身延时 + ",\n");

            // 蛇手左定义
			writer.write("  \"蛇手左滑动1角度\": " + parameters.蛇手左滑动1角度 + ",\n");
			writer.write("  \"蛇手左滑动1长度\": " + parameters.蛇手左滑动1长度 + ",\n");
			writer.write("  \"蛇手左滑动1时长\": " + parameters.蛇手左滑动1时长 + ",\n");

			writer.write("  \"蛇手左分身延时1\": " + parameters.蛇手左分身延时1 + ",\n");
			writer.write("  \"蛇手左滑动延时2\": " + parameters.蛇手左滑动延时2 + ",\n");

			writer.write("  \"蛇手左滑动2角度\": " + parameters.蛇手左滑动2角度 + ",\n");
			writer.write("  \"蛇手左滑动2长度\": " + parameters.蛇手左滑动2长度 + ",\n");
			writer.write("  \"蛇手左滑动2时长\": " + parameters.蛇手左滑动2时长 + ",\n");

			writer.write("  \"蛇手左分身延时2\": " + parameters.蛇手左分身延时2 + ",\n");
			writer.write("  \"蛇手左滑动延时3\": " + parameters.蛇手左滑动延时3 + ",\n");

			writer.write("  \"蛇手左滑动3角度\": " + parameters.蛇手左滑动3角度 + ",\n");
			writer.write("  \"蛇手左滑动3长度\": " + parameters.蛇手左滑动3长度 + ",\n");
			writer.write("  \"蛇手左滑动3时长\": " + parameters.蛇手左滑动3时长 + ",\n");

			writer.write("  \"蛇手左分身点击次数\": " + parameters.蛇手左分身点击次数 + ",\n");
			writer.write("  \"蛇手左分身点击延时\": " + parameters.蛇手左分身点击延时 + ",\n");

            // 蛇手右定义
			writer.write("  \"蛇手右滑动1角度\": " + parameters.蛇手右滑动1角度 + ",\n");
			writer.write("  \"蛇手右滑动1长度\": " + parameters.蛇手右滑动1长度 + ",\n");
			writer.write("  \"蛇手右滑动1时长\": " + parameters.蛇手右滑动1时长 + ",\n");

			writer.write("  \"蛇手右分身延时1\": " + parameters.蛇手右分身延时1 + ",\n");
			writer.write("  \"蛇手右滑动延时2\": " + parameters.蛇手右滑动延时2 + ",\n");

			writer.write("  \"蛇手右滑动2角度\": " + parameters.蛇手右滑动2角度 + ",\n");
			writer.write("  \"蛇手右滑动2长度\": " + parameters.蛇手右滑动2长度 + ",\n");
			writer.write("  \"蛇手右滑动2时长\": " + parameters.蛇手右滑动2时长 + ",\n");

			writer.write("  \"蛇手右分身延时2\": " + parameters.蛇手右分身延时2 + ",\n");
			writer.write("  \"蛇手右滑动延时3\": " + parameters.蛇手右滑动延时3 + ",\n");

			writer.write("  \"蛇手右滑动3角度\": " + parameters.蛇手右滑动3角度 + ",\n");
			writer.write("  \"蛇手右滑动3长度\": " + parameters.蛇手右滑动3长度 + ",\n");
			writer.write("  \"蛇手右滑动3时长\": " + parameters.蛇手右滑动3时长 + ",\n");

			writer.write("  \"蛇手右分身点击次数\": " + parameters.蛇手右分身点击次数 + ",\n");
			writer.write("  \"蛇手右分身点击延时\": " + parameters.蛇手右分身点击延时 + ",\n");


            // 配置参数补全
			writer.write("  \"四分测合左1角度\": " + parameters.四分测合左1角度 + ",\n");
			writer.write("  \"四分测合左1滑动长度\": " + parameters.四分测合左1滑动长度 + ",\n");
			writer.write("  \"四分测合左1滑动时长\": " + parameters.四分测合左1滑动时长 + ",\n");

			writer.write("  \"四分测合右2分前延时\": " + parameters.四分测合左2分前延时 + ",\n");
			writer.write("  \"四分测合左2分身延时\": " + parameters.四分测合左2分身延时 + ",\n");
			writer.write("  \"四分测合右2分后延时\": " + parameters.四分测合左2分后延时 + ",\n");

			writer.write("  \"四分测合左2角度\": " + parameters.四分测合左2角度 + ",\n");
			writer.write("  \"四分测合左2滑动长度\": " + parameters.四分测合左2滑动长度 + ",\n");
			writer.write("  \"四分测合左2滑动时长\": " + parameters.四分测合左2滑动时长 + ",\n");
			writer.write("  \"四分测合左点击分身次数\": " + parameters.四分测合左点击分身次数 + ",\n");
			writer.write("  \"四分测合左点击延时\": " + parameters.四分测合左点击延时 + ", \n");

			writer.write("  \"四分测合右1角度\": " + parameters.四分测合右1角度 + ",\n");
			writer.write("  \"四分测合右1滑动长度\": " + parameters.四分测合右1滑动长度 + ",\n");
			writer.write("  \"四分测合右1滑动时长\": " + parameters.四分测合右1滑动时长 + ",\n");

			writer.write("  \"四分测合右2分前延时\": " + parameters.四分测合右2分前延时 + ",\n");
			writer.write("  \"四分测合右2分身延时\": " + parameters.四分测合右2分延时 + ",\n");
			writer.write("  \"四分测合右2分后延时\": " + parameters.四分测合右2分后延时 + ",\n");

			writer.write("  \"四分测合右2角度\": " + parameters.四分测合右2角度 + ",\n");
			writer.write("  \"四分测合右2滑动长度\": " + parameters.四分测合右2滑动长度 + ",\n");
			writer.write("  \"四分测合右2滑动时长\": " + parameters.四分测合右2滑动时长 + ",\n");
			writer.write("  \"四分测合右点击分身次数\": " + parameters.四分测合右点击分身次数 + ",\n");
			writer.write("  \"四分测合右点击延时\": " + parameters.四分测合右点击延时 + ",\n");
            
            writer.write("  \"后仰左滑动1角度\": " + parameters.后仰左滑动1角度 + ",\n");
writer.write("  \"后仰左滑动1长度\": " + parameters.后仰左滑动1长度 + ",\n");
writer.write("  \"后仰左滑动1时长\": " + parameters.后仰左滑动1时长 + ",\n");

writer.write("  \"后仰左分身延时1\": " + parameters.后仰左分身延时1 + ",\n");
writer.write("  \"后仰左滑动延时2\": " + parameters.后仰左滑动延时2 + ",\n");

writer.write("  \"后仰左滑动2角度\": " + parameters.后仰左滑动2角度 + ",\n");
writer.write("  \"后仰左滑动2长度\": " + parameters.后仰左滑动2长度 + ",\n");
writer.write("  \"后仰左滑动2时长\": " + parameters.后仰左滑动2时长 + ",\n");

writer.write("  \"后仰左分身延时2\": " + parameters.后仰左分身延时2 + ",\n");
writer.write("  \"后仰左滑动延时3\": " + parameters.后仰左滑动延时3 + ",\n");

writer.write("  \"后仰左滑动3角度\": " + parameters.后仰左滑动3角度 + ",\n");
writer.write("  \"后仰左滑动3长度\": " + parameters.后仰左滑动3长度 + ",\n");
writer.write("  \"后仰左滑动3时长\": " + parameters.后仰左滑动3时长 + ",\n");

writer.write("  \"后仰左分身点击次数\": " + parameters.后仰左分身点击次数 + ",\n");
writer.write("  \"后仰左分身点击延时\": " + parameters.后仰左分身点击延时 + ",\n");

// 后仰右
writer.write("  \"后仰右滑动1角度\": " + parameters.后仰右滑动1角度 + ",\n");
writer.write("  \"后仰右滑动1长度\": " + parameters.后仰右滑动1长度 + ",\n");
writer.write("  \"后仰右滑动1时长\": " + parameters.后仰右滑动1时长 + ",\n");

writer.write("  \"后仰右分身延时1\": " + parameters.后仰右分身延时1 + ",\n");
writer.write("  \"后仰右滑动延时2\": " + parameters.后仰右滑动延时2 + ",\n");

writer.write("  \"后仰右滑动2角度\": " + parameters.后仰右滑动2角度 + ",\n");
writer.write("  \"后仰右滑动2长度\": " + parameters.后仰右滑动2长度 + ",\n");
writer.write("  \"后仰右滑动2时长\": " + parameters.后仰右滑动2时长 + ",\n");

writer.write("  \"后仰右分身延时2\": " + parameters.后仰右分身延时2 + ",\n");
writer.write("  \"后仰右滑动延时3\": " + parameters.后仰右滑动延时3 + ",\n");

writer.write("  \"后仰右滑动3角度\": " + parameters.后仰右滑动3角度 + ",\n");
writer.write("  \"后仰右滑动3长度\": " + parameters.后仰右滑动3长度 + ",\n");
writer.write("  \"后仰右滑动3时长\": " + parameters.后仰右滑动3时长 + ",\n");

writer.write("  \"后仰右分身点击次数\": " + parameters.后仰右分身点击次数 + ",\n");
writer.write("  \"后仰右分身点击延时\": " + parameters.后仰右分身点击延时 + ",\n");
            
writer.write("  \"旋转左滑动1角度\": " + parameters.旋转左滑动1角度 + ",\n");
writer.write("  \"旋转左滑动1长度\": " + parameters.旋转左滑动1长度 + ",\n");
writer.write("  \"旋转左滑动1时长\": " + parameters.旋转左滑动1时长 + ",\n");

writer.write("  \"旋转左分身延时1\": " + parameters.旋转左分身延时1 + ",\n");
writer.write("  \"旋转左滑动延时2\": " + parameters.旋转左滑动延时2 + ",\n");

writer.write("  \"旋转左滑动2角度\": " + parameters.旋转左滑动2角度 + ",\n");
writer.write("  \"旋转左滑动2长度\": " + parameters.旋转左滑动2长度 + ",\n");
writer.write("  \"旋转左滑动2时长\": " + parameters.旋转左滑动2时长 + ",\n");

writer.write("  \"旋转左分身延时2\": " + parameters.旋转左分身延时2 + ",\n");
writer.write("  \"旋转左滑动延时3\": " + parameters.旋转左滑动延时3 + ",\n");

writer.write("  \"旋转左滑动3角度\": " + parameters.旋转左滑动3角度 + ",\n");
writer.write("  \"旋转左滑动3长度\": " + parameters.旋转左滑动3长度 + ",\n");
writer.write("  \"旋转左滑动3时长\": " + parameters.旋转左滑动3时长 + ",\n");

writer.write("  \"旋转左分身延时3\": " + parameters.旋转左分身延时3 + ",\n");
writer.write("  \"旋转左滑动延时4\": " + parameters.旋转左滑动延时4 + ",\n");

writer.write("  \"旋转左滑动4角度\": " + parameters.旋转左滑动4角度 + ",\n");
writer.write("  \"旋转左滑动4长度\": " + parameters.旋转左滑动4长度 + ",\n");
writer.write("  \"旋转左滑动4时长\": " + parameters.旋转左滑动4时长 + ",\n");

writer.write("  \"旋转左摇杆点击延时4\": " + parameters.旋转左摇杆点击延时4 + ",\n");
writer.write("  \"旋转左分身连击延时\": " + parameters.旋转左分身连击延时 + ",\n");

writer.write("  \"旋转左分身点击次数\": " + parameters.旋转左分身点击次数 + ",\n");
writer.write("  \"旋转左分身点击延时\": " + parameters.旋转左分身点击延时 + ",\n");
            
           writer.write("  \"旋转右滑动1角度\": " + parameters.旋转右滑动1角度 + ",\n");
writer.write("  \"旋转右滑动1长度\": " + parameters.旋转右滑动1长度 + ",\n");
writer.write("  \"旋转右滑动1时长\": " + parameters.旋转右滑动1时长 + ",\n");

writer.write("  \"旋转右分身延时1\": " + parameters.旋转右分身延时1 + ",\n");
writer.write("  \"旋转右滑动延时2\": " + parameters.旋转右滑动延时2 + ",\n");

writer.write("  \"旋转右滑动2角度\": " + parameters.旋转右滑动2角度 + ",\n");
writer.write("  \"旋转右滑动2长度\": " + parameters.旋转右滑动2长度 + ",\n");
writer.write("  \"旋转右滑动2时长\": " + parameters.旋转右滑动2时长 + ",\n");

writer.write("  \"旋转右分身延时2\": " + parameters.旋转右分身延时2 + ",\n");
writer.write("  \"旋转右滑动延时3\": " + parameters.旋转右滑动延时3 + ",\n");

writer.write("  \"旋转右滑动3角度\": " + parameters.旋转右滑动3角度 + ",\n");
writer.write("  \"旋转右滑动3长度\": " + parameters.旋转右滑动3长度 + ",\n");
writer.write("  \"旋转右滑动3时长\": " + parameters.旋转右滑动3时长 + ",\n");

writer.write("  \"旋转右分身延时3\": " + parameters.旋转右分身延时3 + ",\n");
writer.write("  \"旋转右滑动延时4\": " + parameters.旋转右滑动延时4 + ",\n");

writer.write("  \"旋转右滑动4角度\": " + parameters.旋转右滑动4角度 + ",\n");
writer.write("  \"旋转右滑动4长度\": " + parameters.旋转右滑动4长度 + ",\n");
writer.write("  \"旋转右滑动4时长\": " + parameters.旋转右滑动4时长 + ",\n");

writer.write("  \"旋转右摇杆点击延时4\": " + parameters.旋转右摇杆点击延时4 + ",\n");
writer.write("  \"旋转右分身连击延时\": " + parameters.旋转右分身连击延时 + ",\n");

writer.write("  \"旋转右分身点击次数\": " + parameters.旋转右分身点击次数 + ",\n");
writer.write("  \"旋转右分身点击延时\": " + parameters.旋转右分身点击延时 + "\n");




			writer.write("}\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    private static void saveConfig(parameters parameters, File file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(parameters);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static parameters loadConfigFromFile(File file) {
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                return gson.fromJson(reader, parameters.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; 
    }
}
