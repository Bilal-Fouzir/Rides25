package exceptions;
public class EmailAlreadyExistsException extends Exception {
 private static final long serialVersionUID = 1L;
 
 public EmailAlreadyExistsException()
  {
    super();
  }
  /**This exception is triggered if the question already exists 
  *@param s String of the exception
  */
  public EmailAlreadyExistsException(String s)
  {
    super(s);
  }
}