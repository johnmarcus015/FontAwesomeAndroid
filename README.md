## How to use

1. Past assets folder to your app folder. Same level of src folder.
2. Past class InfoApp.java to your project  
3. Past icons.xml in your res/values folder
4. To use icons you need to create a textView, like: 
  ~~~~
  <TextView
      android:id="@+id/textView1"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:text="@string/fa_icon_calendar" />
  ~~~~
5. To change icons, use strings of icons.xml. If the icon dont exists, declare it with a new line, like: 
  ~~~~
  <string name="name_icon" translatable="false">&#code_icon;</string>
  ~~~~
6. To make textView recognize font awesome, call InfoApp class, like: 
  ~~~~
  InfoApp.enableFontAwesome(getApplicationContext(), findViewById(R.id.textView1));
  ~~~~

**OBS:** You will need call InfoApp class to all textViews you use fontawesome. Example: 
  ~~~~
  InfoApp.enableFontAwesome(getApplicationContext(), findViewById(R.id.textView1));
  InfoApp.enableFontAwesome(getApplicationContext(), findViewById(R.id.textView2));
  InfoApp.enableFontAwesome(getApplicationContext(), findViewById(R.id.textView3));
  InfoApp.enableFontAwesome(getApplicationContext(), findViewById(R.id.textView4));
  ~~~~
