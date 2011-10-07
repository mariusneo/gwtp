package com.gwtplatform.samples.nested.client;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class CurrentUserChangedEvent extends GwtEvent<CurrentUserChangedEvent.CurrentUserChangedHandler> { 

  public interface HasCurrentUserChangedHandlers extends HasHandlers {
    HandlerRegistration addCurrentUserChangedHandler(CurrentUserChangedHandler handler);
  }

  public interface CurrentUserChangedHandler extends EventHandler {
    public void onCurrentUserChanged(CurrentUserChangedEvent event);
  }

  private static final Type<CurrentUserChangedHandler> TYPE = new Type<CurrentUserChangedHandler>();

  public static void fire(HasHandlers source) {
    source.fireEvent(new CurrentUserChangedEvent());
  }

  public static Type<CurrentUserChangedHandler> getType() {
    return TYPE;
  }


  public CurrentUserChangedEvent() {
  }

  @Override
  public Type<CurrentUserChangedHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(CurrentUserChangedHandler handler) {
    handler.onCurrentUserChanged(this);
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "CurrentUserChangedEvent["
    + "]";
  }
}
