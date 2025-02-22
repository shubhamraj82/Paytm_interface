// Generated by view binder compiler. Do not edit!
package com.android.paytmclone.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.android.paytmclone.R;
import com.budiyev.android.codescanner.CodeScannerView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityScannerBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView cardView14;

  @NonNull
  public final CardView cardView15;

  @NonNull
  public final CardView cardView16;

  @NonNull
  public final CardView cardView17;

  @NonNull
  public final CardView cardView18;

  @NonNull
  public final CardView cardView19;

  @NonNull
  public final CardView cardView20;

  @NonNull
  public final ConstraintLayout constraintLayout3;

  @NonNull
  public final EditText editTextPhone;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ImageView imageView6;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final CodeScannerView scanner;

  @NonNull
  public final TextView textView;

  @NonNull
  public final TextView textView2;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final ImageView torch;

  private ActivityScannerBinding(@NonNull ConstraintLayout rootView, @NonNull CardView cardView14,
      @NonNull CardView cardView15, @NonNull CardView cardView16, @NonNull CardView cardView17,
      @NonNull CardView cardView18, @NonNull CardView cardView19, @NonNull CardView cardView20,
      @NonNull ConstraintLayout constraintLayout3, @NonNull EditText editTextPhone,
      @NonNull ImageView imageView, @NonNull ImageView imageView6, @NonNull ConstraintLayout main,
      @NonNull CodeScannerView scanner, @NonNull TextView textView, @NonNull TextView textView2,
      @NonNull TextView textView4, @NonNull ImageView torch) {
    this.rootView = rootView;
    this.cardView14 = cardView14;
    this.cardView15 = cardView15;
    this.cardView16 = cardView16;
    this.cardView17 = cardView17;
    this.cardView18 = cardView18;
    this.cardView19 = cardView19;
    this.cardView20 = cardView20;
    this.constraintLayout3 = constraintLayout3;
    this.editTextPhone = editTextPhone;
    this.imageView = imageView;
    this.imageView6 = imageView6;
    this.main = main;
    this.scanner = scanner;
    this.textView = textView;
    this.textView2 = textView2;
    this.textView4 = textView4;
    this.torch = torch;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityScannerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityScannerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_scanner, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityScannerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardView14;
      CardView cardView14 = ViewBindings.findChildViewById(rootView, id);
      if (cardView14 == null) {
        break missingId;
      }

      id = R.id.cardView15;
      CardView cardView15 = ViewBindings.findChildViewById(rootView, id);
      if (cardView15 == null) {
        break missingId;
      }

      id = R.id.cardView16;
      CardView cardView16 = ViewBindings.findChildViewById(rootView, id);
      if (cardView16 == null) {
        break missingId;
      }

      id = R.id.cardView17;
      CardView cardView17 = ViewBindings.findChildViewById(rootView, id);
      if (cardView17 == null) {
        break missingId;
      }

      id = R.id.cardView18;
      CardView cardView18 = ViewBindings.findChildViewById(rootView, id);
      if (cardView18 == null) {
        break missingId;
      }

      id = R.id.cardView19;
      CardView cardView19 = ViewBindings.findChildViewById(rootView, id);
      if (cardView19 == null) {
        break missingId;
      }

      id = R.id.cardView20;
      CardView cardView20 = ViewBindings.findChildViewById(rootView, id);
      if (cardView20 == null) {
        break missingId;
      }

      id = R.id.constraintLayout3;
      ConstraintLayout constraintLayout3 = ViewBindings.findChildViewById(rootView, id);
      if (constraintLayout3 == null) {
        break missingId;
      }

      id = R.id.editTextPhone;
      EditText editTextPhone = ViewBindings.findChildViewById(rootView, id);
      if (editTextPhone == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.imageView6;
      ImageView imageView6 = ViewBindings.findChildViewById(rootView, id);
      if (imageView6 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.scanner;
      CodeScannerView scanner = ViewBindings.findChildViewById(rootView, id);
      if (scanner == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.textView2;
      TextView textView2 = ViewBindings.findChildViewById(rootView, id);
      if (textView2 == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.torch;
      ImageView torch = ViewBindings.findChildViewById(rootView, id);
      if (torch == null) {
        break missingId;
      }

      return new ActivityScannerBinding((ConstraintLayout) rootView, cardView14, cardView15,
          cardView16, cardView17, cardView18, cardView19, cardView20, constraintLayout3,
          editTextPhone, imageView, imageView6, main, scanner, textView, textView2, textView4,
          torch);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
