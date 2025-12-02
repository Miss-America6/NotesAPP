public class note {
    String title;
    String content;

    public note(String t, String c){
        this.title = t;
        this.content = c;
    }

    public printNote(){
        System.out.println("Title: "+ this.title);
        System.out.println(content)
    }

    public Object[] toRow(){
        return new Object[] {this.id, this.title, this.content};
    }


}