

```java
@RestController
public class FileUpLoadController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @PostMapping("/upload")
    public Map<String, Object> fileUpLoad(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String originalName = file.getOriginalFilename();
        if (!originalName.endsWith(".png")) {
            result.put("status", "500");
            result.put("msg", "文件类型不对");
            return result;
        }
        String format = sdf.format(new Date());
        String realPath = request.getServletContext().getRealPath("/") + format;
        System.out.println("===>"+realPath);
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String newName = UUID.randomUUID().toString() + ".png";
        try {
            file.transferTo(new File(folder, newName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/" + format+"/" + newName;
            result.put("status","200");
            result.put("msg","上传成功！");
            result.put("url",url);
        } catch (IOException e) {
            result.put("status", "500");
            result.put("msg", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
```

