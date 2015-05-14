定义listview有4步
1.listview也是一种view，在main.xml中加view的定义
<ListView
            android:id="@+id/list_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
    </ListView>
2.定义数据(代码或xml中都行),比如在代码中定义
private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};
3.定义ArrayAdapter,和数据关联
4.listView.setAdapter(adapter);显示