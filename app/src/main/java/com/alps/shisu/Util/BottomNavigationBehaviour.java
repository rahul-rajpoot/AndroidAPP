package com.alps.shisu.Util;

// --Commented out by Inspection START (06-07-2021 04:55 PM):
//public class BottomNavigationBehaviour extends CoordinatorLayout.Behavior {
//
//    public BottomNavigationBehaviour() {
//    }
//
//    public BottomNavigationBehaviour(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        return axes== ViewCompat.SCROLL_AXIS_VERTICAL;
//    }
//
//    @Override
//    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
//        child.setTranslationY(Math.max(0f,
//                Math.min(Float.parseFloat(String.valueOf(child.getHeight())),child.getTranslationY()+dy)));
//    }
//
//    @Override
//    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//        if (dependency instanceof Snackbar.SnackbarLayout)
//            updateSnackbar(child,dependency);
//        return super.layoutDependsOn(parent, child, dependency);
//    }
//
//    private void updateSnackbar(View child, View dependency) {
//        if (dependency.getLayoutParams()instanceof CoordinatorLayout.LayoutParams) {
//            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) dependency.getLayoutParams();
//
//            params.setAnchorId(child.getId());
//            params.anchorGravity= Gravity.TOP;
//            params.gravity=Gravity.TOP;
//            dependency.setLayoutParams(params);
//
//        }
//    }
// --Commented out by Inspection STOP (06-07-2021 04:55 PM)
//}