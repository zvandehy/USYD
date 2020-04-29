package au.edu.sydney.soft3202.tutorials.week9.visitor;

public interface Media {

    //TODO: Implement a hierarchy of media objects - Start with a Media interface, then add as many as you can think of - Book, Textbook, EBook, CD, Vinyl, Manga, DVD, PCGame, etc. - each of these are concrete classes, and use inheritance (EBook extends Book, PCGame extends Game, etc). Override the toString method in each, and add in some basic properties with accessors and mutators like title and author. Propagate these up as far as makes sense - everything should have a title, but CD should have artist, not author, AV (and Move and TV subclasses) should have runtime, and so should CD, but not Textbook, etc.
    //
    //Now that you have your hierarchy and properties, add the following method WITHOUT using Visitor (add it to the Media interface and specialise it down - use your property methods, this.getTitle(), not this.title):
    //
    //public void displayMedia()
    //This method should print the details of the Media in a visually appealing way to standard out.
    //
    //Consider the effort to add this method. Now consider what you would need to do if you wanted a displayMediaVerbose() and displayMediaBrief(). Or a displayMediaOnASingleLine(). Or 'String[] getDetailsArray()'?
    //
    //Your challenge now is to refactor your code to use the Visitor Pattern to achieve displayMedia(), and implement each of above methods.
}
