package com.gwtplatform.samples.nested.client.ui;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public final class WidgetPermissionEnabledUtils {
	private WidgetPermissionEnabledUtils() {
	}

	public static void updateWidget(Widget widget, List<Integer> permissions) {
		if (permissions != null) {
			if (widget instanceof HasWidgets) {
				updateWidgets(((HasWidgets) widget).iterator(), permissions);
			}

			if (widget instanceof IPermissionEnabledWidget) {
				((IPermissionEnabledWidget) widget).updateWidgetAppearance(permissions);
			}
		}
	}

	private static void updateWidgets(Iterator<Widget> widgetIterator,
			List<Integer> permissions) {
		if (widgetIterator != null) {
			while (widgetIterator.hasNext()) {
				Widget widget = widgetIterator.next();
				updateWidget(widget, permissions);
			}
		}
	}
}
